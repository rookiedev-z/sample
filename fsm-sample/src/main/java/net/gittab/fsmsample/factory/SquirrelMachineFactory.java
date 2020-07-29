package net.gittab.fsmsample.factory;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.conditions.IssueStatusCondition;
import net.gittab.fsmsample.dto.StateMachineDTO;
import net.gittab.fsmsample.dto.StateMachineTransformDTO;
import net.gittab.fsmsample.enums.TransformType;
import net.gittab.fsmsample.service.StateMachineService;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.StateMachineConfiguration;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

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

//    private UntypedStateMachineBuilder builder;

    public UntypedStateMachine create(Long stateMachineId, Long currentNodeId, Class<StateMachineClient> clazz){

        // 单例复用 state machine builder, 但是如果是有多个状态机的时候不能单例
//        if(this.builder == null){
//            synchronized (SquirrelMachineFactory.class){
//                if(this.builder == null){
//                    // config builder
//                    this.builder = getBuilder(stateMachineId, clazz);
//                }
//            }
//        }
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

        List<StateMachineTransformDTO> transforms = stateMachine.getTransforms();

        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(clazz);

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

//    /**
//     * 当状态机有新元素加入或者有元素被删除需要根据最新的元素重新构建 builder.
//     * @param stateMachineId state machine id
//     * @param clazz state machine class
//     */
//    public void recreateBuilder(Long stateMachineId, Class<StateMachineClient> clazz){
//        synchronized (SquirrelMachineFactory.class) {
//            this.builder = getBuilder(stateMachineId, clazz);
//        }
//    }

}
