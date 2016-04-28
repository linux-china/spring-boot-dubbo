package com.alibaba.dubbo.config.annotation;

import java.lang.annotation.*;

/**
 * dubbo consumer
 *
 * @author linux_china
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DubboConsumer {

    String version() default "";

    int timeout() default 3000;
}
