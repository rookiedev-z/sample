package net.gittab.fsmsample.processor;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import net.gittab.fsmsample.factory.StateMachineClient;
import org.squirrelframework.foundation.component.SquirrelPostProcessor;
import org.squirrelframework.foundation.component.SquirrelPostProcessorProvider;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

/**
 * StateMachineAutowireProcessor.
 *
 * @author rookie dev
 * @date 2020/7/29 00:41
 **/
@Slf4j
@Component
public class StateMachineAutowireProcessor implements SquirrelPostProcessor<UntypedStateMachine>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public StateMachineAutowireProcessor() {
        // register StateMachineAutowireProcessor as state machine post processor
        SquirrelPostProcessorProvider.getInstance().register(StateMachineClient.class, this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcess(UntypedStateMachine untypedStateMachine) {
        Preconditions.checkNotNull(untypedStateMachine);
        // after state machine instance created,
        // autowire @Autowired/@Value dependencies and properties within state machine class
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.autowireBean(untypedStateMachine);
    }
}
