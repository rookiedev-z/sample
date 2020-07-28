package net.gittab.fsmsample.model;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * InvokeBean.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 1:07 下午
 **/
@Data
public class InvokeBean {

    private String code;

    private Object object;

    private Method method;
}
