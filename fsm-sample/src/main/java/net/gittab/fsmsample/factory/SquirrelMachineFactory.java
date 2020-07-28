package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.conditions.IssueStatusCondition;
import net.gittab.fsmsample.domain.StateMachineNode;
import net.gittab.fsmsample.dto.StateMachineDTO;
import net.gittab.fsmsample.dto.StateMachineTransformDTO;
import net.gittab.fsmsample.enums.NodeType;
import net.gittab.fsmsample.enums.TransformType;
import net.gittab.fsmsample.service.StateMachineNodeService;
import net.gittab.fsmsample.service.StateMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

import java.util.List;
import java.util.Objects;

/**
 * StateMachineFactory.
 *
 * @author xiaohua zhou
 **/
@Slf4j
@Component
public class SquirrelMachineFactory {

    @Autowired
    private IssueStatusCondition issueStatusCondition;

    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private StateMachineNodeService nodeService;

    public UntypedStateMachine create(Class<StateMachineClient> clazz, Long stateMachineId, Long currentNodeId){

        // 查找当前状态机下面的所有状态节点和转换方式
        StateMachineDTO stateMachine = stateMachineService.findById(stateMachineId);

        List<StateMachineTransformDTO> transforms = stateMachine.getTransforms();

        // 获取开始节点
        StateMachineNode initialNode = nodeService.getInitNodeId(stateMachineId, NodeType.START);

        // config builder
        UntypedStateMachineBuilder builder = getBuilder(initialNode.getId(), transforms, clazz);

        return builder.newStateMachine(currentNodeId);

    }

    public UntypedStateMachineBuilder getBuilder(Long initialNodeId, List<StateMachineTransformDTO> transforms, Class<StateMachineClient> clazz){
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(clazz);

        builder.onEntry(initialNodeId).callMethod("initial");

        for (StateMachineTransformDTO transform : transforms) {
            if (Objects.equals(transform.getType(), TransformType.ALL.getValue())) {
                // 若是全部转换类型，则所有状态都可达该转换的目标状态节点
                builder.transit().fromAny().to(transform.getEndNodeId()).on(transform.getId())
                        .when(this.issueStatusCondition)
                        .callMethod("issueStatusUpdate");
            } else {
                // 转换都是通过 id 配置
                Long eventId = transform.getId();
                Long sourceId = transform.getStartNodeId();
                Long targetId = transform.getEndNodeId();
                builder.externalTransition().from(sourceId).to(targetId).on(eventId)
                        .when(this.issueStatusCondition)
                        .callMethod("issueStatusUpdate");
            }
        }
        return builder;
    }

}
