package net.gittab.fsmsample.factory;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.model.TransformMessage;
import net.gittab.fsmsample.service.StateMachineClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
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

    /**
     * 由于 state machine 实例并不是通过 spring 创建的，该 autowired 的 stateMachineClientService bean
     * 只能在 state machine 实例被创建成功之后通过 autowireCapableBeanFactory 注入进来
     */
    @Autowired
    private StateMachineClientService stateMachineClientService;

    @Autowired
    private ApplicationContext applicationContext;

    public void initial(Long source, Long target, Long event, TransformMessage context) {
        log.info("===============issue created, status initial {}", target);
    }

    @Transactional(rollbackFor = Exception.class)
    public void issueStatusUpdate(Long source, Long target, Long event, TransformMessage context){
        log.info("==============state machine action execute, issue {} status updated, source {} to target {} by {}", context.getIssueId(), source, target, event);
        // 这里持久化数据
        this.stateMachineClientService.action(event, target, context);
        if(true){
            throw new IllegalStateException("state exception");
        }

//        this.stateMachineClientService.action(event, target, context);
    }

    @Override
    public void fire(Object event) {
        DataSourceTransactionManager transactionManager = applicationContext.getBean("transactionManager", DataSourceTransactionManager.class);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            super.fire(event);
            transactionManager.commit(status);
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Override
    protected void beforeTransitionBegin(Object fromState, Object event, Object context) {
        log.info("==============state machine transform begin before");
    }

//    public void exit3(Long source, Long target, Long event, TransformMessage context){
//        log.info("==============state machine transform exit source {}", source);
//    }
//
//    public void entry4(Long source, Long target, Long event, TransformMessage context){
//        log.info("==============state machine transform entry target {}", target);
//    }
//
//    public void exit4(Long source, Long target, Long event, TransformMessage context){
//        log.info("==============state machine transform exit source {}", source);
//    }
//
//    public void entry3(Long source, Long target, Long event, TransformMessage context){
//        log.info("==============state machine transform entry target {}", target);
//    }

    @Override
    protected void afterTransitionCompleted(Object fromState, Object toState, Object event, Object context) {
        log.info("==============state machine transform completed");
        if(fromState instanceof Long && toState instanceof Long
                && event instanceof Long && context instanceof TransformMessage){
            // 这里持久化数据
            // this.stateMachineClientService.action((Long) event, (Long) toState, (TransformMessage) context);
        }
    }

    @Override
    protected void afterTransitionEnd(Object fromState, Object toState, Object event, Object context) {
        log.info("==============state machine transform end");
        // 这里做一些后置处理动作

    }

    @Override
    protected void afterTransitionDeclined(Object fromState, Object event, Object context) {
        log.info("==============state machine transform declined");
    }

    @Override
    protected void afterTransitionCausedException(Object fromState, Object toState, Object event, Object context) {
//        // 状态机出现异常的时的恢复逻辑
//        Throwable targetException = getLastException().getTargetException();
//        // recover from IllegalArgumentException thrown out from state 'A' to 'B' caused by event 'ToB'
//        if(targetException instanceof IllegalStateException) {
//            // do some error clean up job here
//            log.info("do some error clean up job here to process exception");
//            // after recovered from this exception, reset the state machine status back to normal
//            setStatus(StateMachineStatus.IDLE);
//        } else {
//
//        }
        super.afterTransitionCausedException(fromState, toState, event, context);
    }
}
