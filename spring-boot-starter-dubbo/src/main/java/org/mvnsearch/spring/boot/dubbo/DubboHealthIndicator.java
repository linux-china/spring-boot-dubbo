package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.rpc.service.EchoService;
import org.mvnsearch.spring.boot.dubbo.listener.ConsumerSubscribeListener;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
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
public class DubboHealthIndicator extends AbstractHealthIndicator implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (!ConsumerSubscribeListener.subscribedInterfaces.isEmpty()) {
            for (Class clazz : ConsumerSubscribeListener.subscribedInterfaces) {
                EchoService echoService = (EchoService) applicationContext.getBean(clazz);
                echoService.$echo("Hello");
                builder.withDetail(clazz.getCanonicalName(), true);
            }
        }
        builder.up();
    }

}
