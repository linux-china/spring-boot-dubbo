package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * dubbo auto configuration
 *
 * @author linux_china
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private final DubboProperties properties;

    @Autowired
    private DubboProperties properties;
    @Autowired
    private Environment env;
    public DubboAutoConfiguration(DubboProperties properties) {this.properties = properties;}

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig dubboApplicationConfig() {
        ApplicationConfig appConfig = new ApplicationConfig();
        if (properties.getApp() == null || properties.getApp().isEmpty()) {
            properties.setApp(env.getProperty("spring.application.name"));
        }
        appConfig.setName(properties.getApp());
        return appConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProtocolConfig dubboProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(properties.getProtocol());
        protocolConfig.setTransporter(properties.getTransporter());
        protocolConfig.setPort(properties.getPort());
        protocolConfig.setExportHost(properties.getExportHost());
        protocolConfig.setExportPort(properties.getExportPort());
        protocolConfig.setThreads(properties.getThreads());
        return protocolConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig dubboRegistryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(properties.getRegistry());
        registryConfig.setCheck(true);
        return registryConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.dubbo", name = "monitor")
    public MonitorConfig dubboMonitorConfig() {
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setAddress(properties.getMonitor());
        return monitorConfig;
    }

    @Bean
    public DubboOperationEndpoint dubboOperationEndpoint() {
        return new DubboOperationEndpoint();
    }

    @Bean
    public DubboHealthIndicator dubboHealthIndicator() {
        return new DubboHealthIndicator();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public DubboEndpoint dubboEndpoint() {
        return new DubboEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public DubboOfflineEndpoint dubboOfflineEndpoint() {
        return new DubboOfflineEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public DubboOnlineEndpoint dubboOnlineEndpoint() {
        return new DubboOnlineEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean
    public DubboHealthIndicator dubboHealthIndicator() {
        return new DubboHealthIndicator();
    }

}
