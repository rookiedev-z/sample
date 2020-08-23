package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.model.TransformMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

/**
 * StateMachineClient.
 *
 * @author rookiedev
 * @date 2020/7/28 2:41 下午
 **/
@Slf4j
@Component
public class StateMachineInstance {

    @Autowired
    private MachineFactory machineFactory;

    @Autowired
    private SquirrelMachineFactory squirrelMachineFactory;

    /**
     * execute transform by spring state machine.
     * @param stateMachineId state machine id
     * @param transformId transform id
     * @param currentNodeId current node id
     * @param transformMessage transform message context
     */
    public void executeTransform(Long stateMachineId, Long transformId, Long currentNodeId, TransformMessage transformMessage){

        // 构建 state machine
        StateMachine<String, String> stateMachine = this.machineFactory.buildStateMachine(stateMachineId);

        // 从指定的状态启动，这里可以做缓存，然后通过定时任务来定时清理
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(access ->
                        access.resetStateMachine(new DefaultStateMachineContext<>(currentNodeId.toString(), null, null, null, null, stateMachine.getId())));

        Message<String> message = MessageBuilder.withPayload(transformId.toString()).setHeader(MachineFactory.STATE_MACHINE_HEADER_KEY, transformMessage).build();

        // 触发转换事件
        stateMachine.sendEvent(message);
    }

    /**
     * execute transform by squirrel state machine.
     * @param stateMachineId state machine id
     * @param transformId transform id
     * @param currentNodeId current node id
     * @param transformMessage transform message context
     */
    public void executeSquirrelTransform(Long stateMachineId, Long transformId, Long currentNodeId, TransformMessage transformMessage){

        // 从指定的状态构建 state machine
        UntypedStateMachine stateMachine = this.squirrelMachineFactory.create(stateMachineId, currentNodeId, StateMachineClient.class);

        // 触发转换事件
        stateMachine.fire(transformId, transformMessage);
    }
}
