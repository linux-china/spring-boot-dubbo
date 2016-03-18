package org.mvnsearch.spring.boot.dubbo;

import java.lang.annotation.*;

/**
 * eanble dubbo provider
 *
 * @author linux_china
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableDubboProvider {
}
