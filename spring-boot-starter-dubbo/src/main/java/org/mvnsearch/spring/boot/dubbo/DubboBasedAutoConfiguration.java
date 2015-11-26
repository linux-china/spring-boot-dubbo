package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.BeansException;

/**
 * dubbo based auto configuration
 *
 * @author linux_china
 */
public class DubboBasedAutoConfiguration {

    /**
     * 生成Dubbo的reference bean
     *
     * @param interfaceClazz interface class
     * @param version        版本号
     * @param timeout        超时时间
     * @param <T>            服务接口
     * @return reference bean
     * @throws BeansException
     */
    protected <T> ReferenceBean<T> getConsumerBean(Class<T> interfaceClazz, String version, Integer timeout) throws BeansException {
        ReferenceBean<T> consumerBean = new ReferenceBean<T>();
        String canonicalName = interfaceClazz.getCanonicalName();
        consumerBean.setInterface(canonicalName);
        consumerBean.setId(canonicalName);
        consumerBean.setVersion(version);
        consumerBean.setTimeout(timeout);
        return consumerBean;
    }
}
