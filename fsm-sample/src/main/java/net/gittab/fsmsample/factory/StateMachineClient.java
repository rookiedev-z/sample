package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.model.TransformMessage;
import net.gittab.fsmsample.service.StateMachineClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * StateMachineClient.
 *
 * @author rookie dev
 * @date 2020/7/28 23:21
 **/
@Slf4j
@StateMachineParameters(stateType = Long.class, eventType = Long.class, contextType = TransformMessage.class)
public class StateMachineClient extends AbstractUntypedStateMachine {

    @Autowired
    private StateMachineClientService stateMachineClientService;

    public void initial(Long source, Long target, Long event, TransformMessage context) {
        log.info("===============issue created, status initial {}", target);
    }

    public void issueStatusUpdate(Long source, Long target, Long event, TransformMessage context){
        log.info("===============issue {} status updated, source {} to target {} by {}", context.getIssueId(), source, target, event);

        this.stateMachineClientService.action(event, target, context);
    }

    @Override
    protected void afterTransitionCausedException(Object fromState, Object toState, Object event, Object context) {
        Throwable targetException = getLastException().getTargetException();
        // recover from IllegalArgumentException thrown out from state 'A' to 'B' caused by event 'ToB'
        if(targetException instanceof IllegalStateException) {
            // do some error clean up job here
            log.info("do some error clean up job here to process exception");
            // after recovered from this exception, reset the state machine status back to normal
            setStatus(StateMachineStatus.IDLE);
        } else {
            super.afterTransitionCausedException(fromState, toState, event, context);
        }
    }


}
