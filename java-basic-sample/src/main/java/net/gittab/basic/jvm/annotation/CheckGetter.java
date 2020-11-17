package net.gittab.basic.jvm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CheckGetter.
 *
 * @author rookiedev 2020/11/10 10:51
 **/
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface CheckGetter {

}
