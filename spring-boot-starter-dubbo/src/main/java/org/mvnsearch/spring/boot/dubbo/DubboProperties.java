package org.mvnsearch.spring.boot.dubbo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * dubbo properties
 *
 * @author linux_china
 */
@ConfigurationProperties(
        prefix = "spring.dubbo"
)
public class DubboProperties {
    private String app;
    private String registry;
    private String protocol;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
