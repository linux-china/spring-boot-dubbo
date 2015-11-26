package org.mvnsearch.uic.boot;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration;
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration;
import org.mvnsearch.uic.UicTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * uic auto configuration
 *
 * @author linux_china
 */
@Configuration
@EnableConfigurationProperties(UicProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class UicDemoAutoconfiguration extends DubboBasedAutoConfiguration {
    @Autowired
    private UicProperties properties;

    @Bean
    public ReferenceBean<UicTemplate> uicTemplate() {
        return getConsumerBean(UicTemplate.class, properties.getVersion(), properties.getTimeout());
    }

}
