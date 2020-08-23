package net.gittab.fsmsample.processor;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.model.InvokeBean;
import net.gittab.fsmsample.model.TransformMessage;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * StateMachineManagement.
 *
 * @author rookiedev
 * @date 2020/7/28 1:05 下午
 **/
@Slf4j
@Component
public class StateMachineManagement {

    private static final Map<String, InvokeBean> INVOKE_BEAN_MAP = new HashMap<>(8);

    public InvokeBean getInvokeBean(String code){
        return INVOKE_BEAN_MAP.get(code);
    }

    public void putInvokeBean(String code, InvokeBean invokeBean){
        INVOKE_BEAN_MAP.put(code, invokeBean);
    }

    public void postAction(TransformMessage message){
        log.info("execute post action, code {}", message.getInvokeCode());
        InvokeBean invokeBean = getInvokeBean(message.getInvokeCode());
        if(invokeBean != null){
            Method method = invokeBean.getMethod();
            try {
                method.invoke(invokeBean.getObject(), message.getIssueId(), message.getAssigneeId());
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("execute post action failed", e);
                throw new IllegalStateException(e.getLocalizedMessage());
            }
        }
    }

}
