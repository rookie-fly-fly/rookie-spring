package com.dyg.rookie.spring.common.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Log 接口是 开启日志记录的注解
 *
 * @author rookie-spring
 * @date 2021-04-13 19:59
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Log {
    String value() default "";
}
