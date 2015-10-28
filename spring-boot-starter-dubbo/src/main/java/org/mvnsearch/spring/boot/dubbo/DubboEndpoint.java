package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dubbo endpoint
 *
 * @author linux_china
 */
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
        List<String> publishedInterfaceList = new ArrayList<String>();
        Map<String, Object> dubboBeans = applicationContext.getBeansWithAnnotation(DubboService.class);
        for (Map.Entry<String, Object> entry : dubboBeans.entrySet()) {
            Object bean = entry.getValue();
            Class<?> interfaceClass = bean.getClass().getAnnotation(DubboService.class).interfaceClass();
            String interfaceClassCanonicalName = interfaceClass.getCanonicalName();
            if (interfaceClassCanonicalName.equals("void")) {
                Class<?>[] interfaces = bean.getClass().getInterfaces();
                if (interfaces != null && interfaces.length == 1) {
                    publishedInterfaceList.add(interfaces[0].getCanonicalName());
                }
            } else {
                publishedInterfaceList.add(interfaceClassCanonicalName);
            }
        }
        info.put("interfaceClasses", publishedInterfaceList);
        return info;
    }
}
