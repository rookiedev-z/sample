package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.dto.StateMachineDTO;
import net.gittab.fsmsample.dto.StateMachineNodeDTO;
import net.gittab.fsmsample.dto.StateMachineTransformDTO;
import net.gittab.fsmsample.enums.NodeType;
import net.gittab.fsmsample.enums.TransformType;
import net.gittab.fsmsample.service.StateMachineNodeService;
import net.gittab.fsmsample.service.StateMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MachineFactory {

    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private StateMachineNodeService nodeService;

    private StateMachineBuilder.Builder<String, String> getBuilder(String serviceCode, Long stateMachineId) {
        StateMachineDTO stateMachine = stateMachineService.findById(stateMachineId);

        List<StateMachineNodeDTO> nodes = stateMachine.getNodes();
        List<StateMachineTransformDTO> transforms = stateMachine.getTransforms();

        Long startNodeId = nodeService.getNodeId(stateMachineId, NodeType.START);

        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        try {
            builder.configureConfiguration()
                    .withConfiguration()
                    .machineId(stateMachineId.toString());
            builder.configureStates()
                    .withStates()
                    .initial(startNodeId.toString(), initialAction())
                    .states(nodes.stream().map(x -> x.getId().toString()).collect(Collectors.toSet()));
            for (StateMachineTransformDTO transform : transforms) {
                if (Objects.equals(transform.getType(), TransformType.ALL.getValue())) {
                    // 若配置了全部转换
                    for (StateMachineNodeDTO node : nodes) {
                        String event = transform.getId().toString();
                        String source = node.getId().toString();
                        String target = transform.getEndNodeId().toString();
                        builder.configureTransitions()
                                .withExternal()
                                .source(source).target(target)
                                .event(event)
                                .action(action(), errorAction())
                                .guard(guard());
                    }
                } else {
                    // 转换都是通过 id 配置
                    String event = transform.getId().toString();
                    String source = transform.getStartNodeId().toString();
                    String target = transform.getEndNodeId().toString();
                    builder.configureTransitions()
                            .withExternal()
                            .source(source).target(target)
                            .event(event)
                            .action(action(), errorAction())
                            .guard(guard());
                }

            }
        } catch (Exception e) {
            log.error("build StateMachineBuilder error,exception:{},stateMachineId:{}", e, stateMachineId);
        }
        return builder;
    }

    private Action<String, String> initialAction(){
        return stateContext -> MachineFactory.log.info("stateMachine instance execute initialAction");
    }

    private Action<String, String> action(){
        return stateContext -> MachineFactory.log.info("stateMachine instance execute action");
    }

    private Action<String, String> errorAction(){
        return stateContext -> MachineFactory.log.info("stateMachine instance execute errorAction");
    }

    private Guard<String, String> guard(){
        return stateContext -> {
            MachineFactory.log.info("stateMachine instance execute guard");
            return true;
        };
    }
}
