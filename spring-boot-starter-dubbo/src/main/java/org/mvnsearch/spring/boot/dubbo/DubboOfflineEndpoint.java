package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;
import org.mvnsearch.spring.boot.dubbo.listener.ProviderExportListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import javax.annotation.PostConstruct;

/**
 * dubbo operation endpoint
 *
 * @author linux_china
 */
@Endpoint(id = "dubbo-offline")
public class DubboOfflineEndpoint {
    @Autowired
    private DubboProperties properties;
    private Registry registry;

    @PostConstruct
    public void init() {
        ExtensionLoader<RegistryFactory> extensionLoader = ExtensionLoader.getExtensionLoader(RegistryFactory.class);
        URL url = URL.valueOf(properties.getRegistry());
        RegistryFactory registryFactory = extensionLoader.getExtension(url.getProtocol());
        registry = registryFactory.getRegistry(url);
    }

    @ReadOperation
    public String offline() {
        for (URL url : ProviderExportListener.exportedUrl) {
            registry.unregister(url);
        }
        return "success";
    }

}
