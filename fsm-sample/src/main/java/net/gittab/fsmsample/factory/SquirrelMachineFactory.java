package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.conditions.IssueStatusCondition;
import net.gittab.fsmsample.dto.StateMachineDTO;
import net.gittab.fsmsample.dto.StateMachineNodeDTO;
import net.gittab.fsmsample.dto.StateMachineTransformDTO;
import net.gittab.fsmsample.enums.TransformType;
import net.gittab.fsmsample.service.StateMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.StateMachineConfiguration;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

import java.util.List;
import java.util.Objects;

/**
 * StateMachineFactory.
 *
 * @author rookiedev
 **/
@Slf4j
@Component
public class SquirrelMachineFactory {

    @Autowired
    private IssueStatusCondition issueStatusCondition;

    @Autowired
    private StateMachineService stateMachineService;

    public UntypedStateMachine create(Long stateMachineId, Long currentNodeId, Class<StateMachineClient> clazz){

        // 多 pod 部署的情况下可用 redis 来缓存 builder
        UntypedStateMachineBuilder builder = getBuilder(stateMachineId, clazz);

        // state machine instance 不可复用，足够轻量，直接创建
        return builder.newStateMachine(currentNodeId, StateMachineConfiguration.create().enableDebugMode(true).enableAutoStart(true));
    }

    public UntypedStateMachineBuilder getBuilder(Long stateMachineId, Class<StateMachineClient> clazz){

        // builder 可被复用，如果系统启动后没有状态新增或删除可以以单例方式交给 spring container 管理
        // 如果系统启动后有状态新增或删除则可以手动维护 builder 实例，当状态元素发生变化时需要重新构建 builder

        // 查找当前状态机下面的所有状态节点和转换方式
        StateMachineDTO stateMachine = stateMachineService.findById(stateMachineId);

        List<StateMachineNodeDTO> nodes = stateMachine.getNodes();

        List<StateMachineTransformDTO> transforms = stateMachine.getTransforms();

        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(clazz);

        for (StateMachineTransformDTO transform : transforms) {
            // 转换都是通过 id 配置
            Long eventId = transform.getId();
            Long targetId = transform.getEndNodeId();

            if (Objects.equals(transform.getType(), TransformType.ALL.getValue())) {
                // 若是全部转换类型，则所有状态都可达该转换的目标状态节点，可排和除目标节点相同的节点
                Long[] nodeIds = nodes.stream().filter(item -> !targetId.equals(item.getId())).map(StateMachineNodeDTO::getId).toArray(Long[]::new);
                builder.externalTransitions().fromAmong(nodeIds).to(targetId).on(transform.getId())
                        .when(this.issueStatusCondition)
                        .callMethod("issueStatusUpdate");
            } else {
                // 转换都是通过 id 配置
                Long sourceId = transform.getStartNodeId();
                builder.externalTransition().from(sourceId).to(targetId).on(eventId)
                        .when(this.issueStatusCondition)
                        .callMethod("issueStatusUpdate");
            }
        }
        return builder;
    }

}
