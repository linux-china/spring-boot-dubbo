package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.config.ProtocolConfig;
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
    public static Boolean OFFLINE=false;

    @PostConstruct
    public void init() {
        ExtensionLoader<RegistryFactory> extensionLoader = ExtensionLoader.getExtensionLoader(RegistryFactory.class);
        URL url = URL.valueOf(properties.getRegistry());
        RegistryFactory registryFactory = extensionLoader.getExtension(url.getProtocol());
        registry = registryFactory.getRegistry(url);
    }

    @ReadOperation
    public String offline() {
        ProtocolConfig.destroyAll();
        OFFLINE = true;
        return "sucess";
    }

    public String getPath() {
        return "dubbo";
    }

    public boolean isSensitive() {
        return false;
    }

    public Class<? extends Endpoint> getEndpointType() {
        return null;
    }
        for (URL url : ProviderExportListener.exportedUrl) {
            registry.unregister(url);
        }
        return "success";
    }

}
