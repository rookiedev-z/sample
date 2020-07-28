package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.model.TransformMessage;
import net.gittab.fsmsample.service.StateMachineClientService;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * StateMachineAction.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 4:53 下午
 **/
@Slf4j
public class StateMachineAction implements Action<String, String> {

    private StateMachineClientService stateMachineClientService;

    public StateMachineAction(StateMachineClientService stateMachineClientService){
        this.stateMachineClientService = stateMachineClientService;
    }

    @Override
    public void execute(StateContext<String, String> context) {
        log.info("stateMachine execute action");
        Long transformId = Long.parseLong(context.getEvent());
        Long targetNodeId = Long.parseLong(context.getTarget().getId());

        MessageHeaders headers = context.getMessage().getHeaders();
        TransformMessage message = headers.get(MachineFactory.STATE_MACHINE_HEADER_KEY, TransformMessage.class);

        this.stateMachineClientService.action(transformId, targetNodeId, message);

    }
}
