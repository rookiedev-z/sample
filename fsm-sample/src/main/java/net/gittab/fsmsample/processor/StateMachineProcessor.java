package net.gittab.fsmsample.processor;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.annotation.Condition;
import net.gittab.fsmsample.annotation.PostAction;
import net.gittab.fsmsample.annotation.PostActionClass;
import net.gittab.fsmsample.factory.StateMachineClient;
import net.gittab.fsmsample.model.InvokeBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * StateMachineProcessor.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 11:19 上午
 **/
@Slf4j
@Component
public class StateMachineProcessor implements BeanPostProcessor {

    @Autowired
    private StateMachineManagement stateMachineManagement;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof StateMachineClient){
            log.info("=======================StateMachineClient post process after initialization=======================");
        }
        if(bean instanceof AbstractAction && bean.getClass().isAnnotationPresent(PostActionClass.class)){
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
            log.info("=======================post process after initialization=======================");
            for (Method method : methods) {
                InvokeBean invokeBean = new InvokeBean();
                invokeBean.setObject(bean);
                invokeBean.setMethod(method);

                Condition condition = AnnotationUtils.getAnnotation(method, Condition.class);
                if(condition != null){
                    log.info("=======================init state machine condition, code {}", condition.code());
                    invokeBean.setCode(condition.code());
                    this.stateMachineManagement.putInvokeBean(condition.code(), invokeBean);
                }

                PostAction action = AnnotationUtils.getAnnotation(method, PostAction.class);
                if(action != null){
                    log.info("=======================init state machine action, code {}", action.code());
                    invokeBean.setCode(action.code());
                    this.stateMachineManagement.putInvokeBean(action.code(), invokeBean);
                }
            }
        }
        return bean;
    }
}
