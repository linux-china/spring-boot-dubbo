package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.config.annotation.DubboService;
import org.mvnsearch.spring.boot.dubbo.listener.ConsumerInvokeStaticsFilter;
import org.mvnsearch.spring.boot.dubbo.listener.ConsumerSubscribeListener;
import org.mvnsearch.spring.boot.dubbo.listener.ProviderExportListener;
import org.mvnsearch.spring.boot.dubbo.listener.ProviderInvokeStaticsFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * dubbo endpoint
 *
 * @author linux_china
 */
@Component
public class DubboEndpoint extends AbstractEndpoint implements ApplicationContextAware {
    private DubboProperties dubboProperties;
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setDubboProperties(DubboProperties dubboProperties) {
        this.dubboProperties = dubboProperties;
    }

    public DubboEndpoint() {
        super("dubbo", false, true);
    }

    public Object invoke() {
        Map<String, Object> info = new HashMap<String, Object>();
        Boolean serverMode = false;
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(EnableDubboConfiguration.class);
        if (beanNames != null && beanNames.length > 0) {
            serverMode = true;
        }
        if (serverMode) {
            info.put("server", true);
            info.put("port", dubboProperties.getPort());
        }
        info.put("app", dubboProperties.getApp());
        info.put("registry", dubboProperties.getRegistry());
        info.put("protocol", dubboProperties.getProtocol());
        //published services
        Map<String, Map<String, Long>> publishedInterfaceList = new HashMap<String, Map<String, Long>>();
        Set<Class> publishedInterfaces = ProviderExportListener.exportedInterfaces;
        for (Class clazz : publishedInterfaces) {
            String interfaceClassCanonicalName = clazz.getCanonicalName();
            if (!interfaceClassCanonicalName.equals("void")) {
                Map<String, Long> methodNames = new HashMap<String, Long>();
                for (Method method : clazz.getMethods()) {
                    methodNames.put(method.getName(), ProviderInvokeStaticsFilter.getValue(clazz, method.getName()));
                }
                publishedInterfaceList.put(interfaceClassCanonicalName, methodNames);
            }
        }
        if (!publishedInterfaceList.isEmpty()) {
            info.put("publishedInterfaces", publishedInterfaceList);
        }
        //subscribed services
        Set<Class> subscribedInterfaces = ConsumerSubscribeListener.subscribedInterfaces;
        if (!subscribedInterfaces.isEmpty()) {
            try {
                Map<String, Map<String, Long>> subscribedInterfaceList = new HashMap<String, Map<String, Long>>();
                for (Class clazz : subscribedInterfaces) {
                    Map<String, Long> methodNames = new HashMap<String, Long>();
                    for (Method method : clazz.getMethods()) {
                        methodNames.put(method.getName(), ConsumerInvokeStaticsFilter.getValue(clazz, method.getName()));
                    }
                    subscribedInterfaceList.put(clazz.getCanonicalName(), methodNames);
                }
                info.put("subscribedInterfaces", subscribedInterfaceList);
            } catch (Exception ignore) {

            }
            info.put("connections", ConsumerSubscribeListener.connections);
        }
        return info;
    }
}
