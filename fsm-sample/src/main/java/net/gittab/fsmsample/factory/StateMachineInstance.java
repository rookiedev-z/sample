package net.gittab.fsmsample.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.model.TransformMessage;

/**
 * StateMachineClient.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 2:41 下午
 **/
@Slf4j
@Component
public class StateMachineInstance {

    @Autowired
    private MachineFactory machineFactory;

    public void executeTransform(Long stateMachineId, Long transformId, Long currentStatusId, TransformMessage transformMessage){

        // 构建 state machine
        StateMachine<String, String> stateMachine = this.machineFactory.buildStateMachine(stateMachineId);

        // 从指定的状态启动，这里可以做缓存，然后通过定时任务来定时清理
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(access ->
                        access.resetStateMachine(new DefaultStateMachineContext<>(currentStatusId.toString(), null, null, null, null, stateMachine.getId())));

        Message<String> message = MessageBuilder.withPayload(transformId.toString()).setHeader(MachineFactory.STATE_MACHINE_HEADER_KEY, transformMessage).build();

        // 触发转换事件
        stateMachine.sendEvent(message);
    }
}
