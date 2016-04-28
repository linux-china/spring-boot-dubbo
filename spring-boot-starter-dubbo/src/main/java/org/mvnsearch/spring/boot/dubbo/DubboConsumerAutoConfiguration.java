package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.DubboConsumer;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.fasterxml.jackson.core.Versioned;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * dubbo consumer auto configuration
 *
 * @author linux_china
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class DubboConsumerAutoConfiguration extends DubboBasedAutoConfiguration implements ApplicationContextAware {
    private Map<String, Object> dubboReferences = new HashMap<>();
    private ApplicationContext applicationContext;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                Class<?> objClz = bean.getClass();
                if (org.springframework.aop.support.AopUtils.isAopProxy(bean)) {
                    objClz = org.springframework.aop.support.AopUtils.getTargetClass(bean);
                }

                for (Field field : objClz.getDeclaredFields()) {
                    DubboConsumer dubboConsumer = field.getAnnotation(DubboConsumer.class);
                    if (dubboConsumer != null) {
                        Class type = field.getType();
                        ReferenceBean consumerBean = getConsumerBean(type, dubboConsumer.version(), dubboConsumer.timeout());
                        String id = type.getCanonicalName() + ":" + dubboConsumer.version();
                        if (!dubboReferences.containsKey(id)) {
                            consumerBean.setApplicationContext(applicationContext);
                            consumerBean.setApplication(applicationConfig);
                            consumerBean.setRegistry(registryConfig);
                            consumerBean.setApplication(applicationConfig);
                            try {
                                consumerBean.afterPropertiesSet();
                                dubboReferences.put(id, consumerBean.getObject());
                            } catch (Exception e) {
                                throw new BeanCreationException(beanName, e);
                            }
                        }
                        try {
                            field.setAccessible(true);
                            field.set(bean, dubboReferences.get(id));
                            field.setAccessible(false);
                        } catch (Exception e) {
                            throw new BeanCreationException(beanName, e);
                        }

                    }
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }
}
