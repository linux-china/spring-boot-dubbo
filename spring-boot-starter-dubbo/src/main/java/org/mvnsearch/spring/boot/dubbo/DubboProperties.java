package org.mvnsearch.spring.boot.dubbo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * dubbo properties
 *
 * @author linux_china
 */
@ConfigurationProperties(prefix = "spring.dubbo")
public class DubboProperties {
    /**
     * dubbo application name
     */
    private String app;
    /**
     * dubbo registry address
     */
    private String registry;
    /**
     * dubbo monitor address
     */
    private String monitor;
    /**
     * http check url
     */
    private String httpCheckUrl;
    /**
     * communication protocol, default is dubbo
     */
    private String protocol = "dubbo";
    /**
     * transporter: netty 3 or netty 4
     */
    private String transporter = "netty";
    /**
     * dubbo listen port, default 20800
     */
    private Integer port = 20800;
    /**
     * dubbo export host, useful when running in Docker
     */
    private String exportHost;
    /**
     * dubbo export port, useful when running in Docker
     */
    private Integer exportPort;
    /**
     * dubbo thread count, default 200
     */
    private Integer threads = 200;

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

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getHttpCheckUrl() {
        return httpCheckUrl;
    }

    public void setHttpCheckUrl(String httpCheckUrl) {
        this.httpCheckUrl = httpCheckUrl;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getExportHost() {
        return exportHost;
    }

    public void setExportHost(String exportHost) {
        this.exportHost = exportHost;
    }

    public Integer getExportPort() {
        return exportPort;
    }

    public void setExportPort(Integer exportPort) {
        this.exportPort = exportPort;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }
}
