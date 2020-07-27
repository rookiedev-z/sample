package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.dto.StateMachineDTO;
import net.gittab.fsmsample.dto.StateMachineNodeDTO;
import net.gittab.fsmsample.dto.StateMachineTransformDTO;
import net.gittab.fsmsample.enums.NodeType;
import net.gittab.fsmsample.service.StateMachineNodeService;
import net.gittab.fsmsample.service.StateMachineService;
import net.gittab.fsmsample.service.StateMachineTransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MachineFactory {

    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private StateMachineNodeService nodeService;

    @Autowired
    private StateMachineTransformService transformService;



    private StateMachineBuilder.Builder<String, String> getBuilder(String serviceCode, Long stateMachineId) {
        StateMachineDTO stateMachine = stateMachineService.findById(stateMachineId);
        List<StateMachineNodeDTO> nodes = stateMachine.getNodes();
        List<StateMachineTransformDTO> transforms = stateMachine.getTransforms();
        Long initNodeId = nodeService.getNodeId(stateMachineId, NodeType.START);

        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        try {
            builder.configureConfiguration()
                    .withConfiguration()
                    .machineId(stateMachineId.toString());
            builder.configureStates()
                    .withStates()
                    .initial(initNodeId.toString(), initialAction(organizationId, serviceCode))
                    .states(nodes.stream().map(x -> x.getId().toString()).collect(Collectors.toSet()));
            for (StateMachineTransformDTO transform : transforms) {
                if (transform.getType().equals(TransformType.ALL)) {
                    //若配置了全部转换
                    for (StateMachineNodeDTO node : nodes) {
                        String event = transform.getId().toString();
                        String source = node.getId().toString();
                        String target = transform.getEndNodeId().toString();
                        builder.configureTransitions()
                                .withExternal()
                                .source(source).target(target)
                                .event(event)
                                .action(action(organizationId, serviceCode), errorAction(organizationId, serviceCode))
                                .guard(guard(organizationId, serviceCode));
                    }
                } else {
                    //转换都是通过id配置
                    String event = transform.getId().toString();
                    String source = transform.getStartNodeId().toString();
                    String target = transform.getEndNodeId().toString();
                    builder.configureTransitions()
                            .withExternal()
                            .source(source).target(target)
                            .event(event)
                            .action(action(organizationId, serviceCode), errorAction(organizationId, serviceCode))
                            .guard(guard(organizationId, serviceCode));
                }

            }
        } catch (Exception e) {
            log.error("build StateMachineBuilder error,exception:{},stateMachineId:{}", e, stateMachineId);
        }
        return builder;

    }
}
