package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
    @Autowired
    private DubboProperties properties;

    @Bean
    public ApplicationConfig dubboApplicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        config.setName(properties.getApp());
        return config;
    }

    @Bean
    public ProtocolConfig dubboProtocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        return config;
    }

    @Bean
    public RegistryConfig dubboRegistryConfig() {
        RegistryConfig config = new RegistryConfig();
        return config;
    }
}
