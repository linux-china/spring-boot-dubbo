package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.rpc.service.EchoService;
import org.mvnsearch.spring.boot.dubbo.listener.ConsumerSubscribeListener;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * dubbo health indicator
 *
 * @author linux_china
 */
@Component
public class DubboHealthIndicator implements HealthIndicator, ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Health health() {
        Health.Builder builder = Health.up();
        if (!ConsumerSubscribeListener.subscribedInterfaces.isEmpty()) {
            try {
                for (Class clazz : ConsumerSubscribeListener.subscribedInterfaces) {
                    EchoService echoService = (EchoService) applicationContext.getBean(clazz);
                    echoService.$echo("Hello");
                    builder.withDetail(clazz.getCanonicalName(), true);
                }
            } catch (Exception e) {
                return Health.down().withDetail("Dubbo", e.getMessage()).build();
            }
        }
        return builder.build();
    }
}
