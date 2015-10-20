package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dubbo auto configuration
 *
 * @author linux_china
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DubboProperties properties;

    @Bean
    public ApplicationConfig dubboApplicationConfig() {
        ApplicationConfig appConfig = new ApplicationConfig();
        appConfig.setName(properties.getApp());
        return appConfig;
    }

    @Bean
    public ProtocolConfig dubboProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        if (properties.getProtocol() == null) {
            properties.setProtocol("dubbo");
        }
        if (properties.getPort() == null) {
            properties.setPort(20800);
        }
        protocolConfig.setName(properties.getProtocol());
        protocolConfig.setPort(properties.getPort());
        protocolConfig.setThreads(200);
        return protocolConfig;
    }

    @Bean
    public RegistryConfig dubboRegistryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(properties.getRegistry());
        return registryConfig;
    }

    @Bean
    public AnnotationBean dubboAnnotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(properties.getScan());
        return annotationBean;
    }
}
