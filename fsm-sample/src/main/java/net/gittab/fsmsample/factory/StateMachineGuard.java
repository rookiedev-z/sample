package net.gittab.fsmsample.factory;

import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.model.TransformMessage;
import net.gittab.fsmsample.service.StateMachineClientService;

/**
 * StateMachineGuard.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 4:54 下午
 **/
@Slf4j
public class StateMachineGuard implements Guard<String, String> {

    private StateMachineClientService stateMachineClientService;

    public StateMachineGuard(StateMachineClientService stateMachineClientService){
        this.stateMachineClientService = stateMachineClientService;
    }

    @Override
    public boolean evaluate(StateContext<String, String> context) {
        log.info("stateMachine instance execute guard");
        Long transformId = Long.parseLong(context.getEvent());

        Long targetNodeId = Long.parseLong(context.getTarget().getId());

        MessageHeaders headers = context.getMessage().getHeaders();
        TransformMessage message = headers.get(MachineFactory.STATE_MACHINE_HEADER_KEY, TransformMessage.class);

        return this.stateMachineClientService.guard(transformId, targetNodeId, message);
    }
}