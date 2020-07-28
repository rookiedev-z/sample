package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.domain.StateMachineNode;
import net.gittab.fsmsample.dto.StateMachineDTO;
import net.gittab.fsmsample.dto.StateMachineNodeDTO;
import net.gittab.fsmsample.dto.StateMachineTransformDTO;
import net.gittab.fsmsample.enums.NodeType;
import net.gittab.fsmsample.enums.TransformType;
import net.gittab.fsmsample.service.StateMachineClientService;
import net.gittab.fsmsample.service.StateMachineNodeService;
import net.gittab.fsmsample.service.StateMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xiaohua zhou
 */
@Slf4j
@Component
public class MachineFactory {

    public static final String STATE_MACHINE_HEADER_KEY = "state_machine_header";

    @Autowired
    private StateMachineClientService stateMachineClientService;

    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private StateMachineNodeService nodeService;

    private StateMachineBuilder.Builder<String, String> getBuilder(Long stateMachineId) {

        // 查找当前状态机下面的所有状态节点和转换方式
        StateMachineDTO stateMachine = stateMachineService.findById(stateMachineId);

        List<StateMachineNodeDTO> nodes = stateMachine.getNodes();
        List<StateMachineTransformDTO> transforms = stateMachine.getTransforms();

        // 获取开始节点
        StateMachineNode stateMachineNode = nodeService.getInitNodeId(stateMachineId, NodeType.START);

        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        try {
            builder.configureConfiguration()
                    .withConfiguration()
                    .machineId(stateMachineId.toString());
            builder.configureStates()
                    .withStates()
                    .initial(stateMachineNode.getId().toString(), initialAction())
                    .states(nodes.stream().map(x -> x.getId().toString()).collect(Collectors.toSet()));
            for (StateMachineTransformDTO transform : transforms) {
                if (Objects.equals(transform.getType(), TransformType.ALL.getValue())) {
                    // 若是全部转换类型，则所有状态都可达该转换的目标状态节点
                    for (StateMachineNodeDTO node : nodes) {
                        String event = transform.getId().toString();
                        String source = node.getId().toString();
                        String target = transform.getEndNodeId().toString();
                        // 过滤掉目标状态节点是自己的节点
                        if (Objects.equals(source, target)) {
                            continue;
                        }
                        builder.configureTransitions()
                                .withExternal()
                                .source(source).target(target)
                                .event(event)
                                .guard(guard())
                                .action(action(), errorAction());
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
                            .guard(guard())
                            .action(action(), errorAction());
                }
            }
        } catch (Exception e) {
            log.error("StateMachineBuilder build error, stateMachineId: {}", stateMachineId, e);
        }
        return builder;
    }

    public StateMachine<String, String> buildStateMachine(Long stateMachineId) {
        StateMachineBuilder.Builder<String, String> builder = getBuilder(stateMachineId);
        StateMachine<String, String> stateMachine = builder.build();
        // start state machine
        stateMachine.start();
        return stateMachine;
    }

    private Action<String, String> initialAction() {
        return stateContext -> MachineFactory.log.info("stateMachine execute initialAction");
    }

    private Action<String, String> action() {
        return new StateMachineAction(stateMachineClientService);
    }

    private Action<String, String> errorAction() {
        return stateContext -> MachineFactory.log.info("stateMachine execute errorAction");
    }

    private Guard<String, String> guard() {
        return new StateMachineGuard(stateMachineClientService);
    }
}
