package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.BeansException;

/**
 * dubbo based auto configuration
 *
 * @author linux_china
 */
public class DubboBasedAutoConfiguration {

   protected  <T> ReferenceBean<T> getConsumerBean(Class<T> interfaceClazz, String version, Integer timeout) throws BeansException {
        ReferenceBean<T> consumerBean = new ReferenceBean<T>();
        String canonicalName = interfaceClazz.getCanonicalName();
        consumerBean.setInterface(canonicalName);
        consumerBean.setId(canonicalName);
        consumerBean.setVersion(version);
        consumerBean.setTimeout(timeout);
        return consumerBean;
    }
}
