package org.mvnsearch.spring.boot.dubbo;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dubbo annotation configuration
 *
 * @author linux_china
 */
@Configuration
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAnnotationConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public AnnotationBean dubboAnnotationBean() {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(EnableDubboConfiguration.class);
        if (beanNames != null) {
            Object entrance = applicationContext.getBean(beanNames[0]);
            String scanPackage = entrance.getClass().getAnnotation(EnableDubboConfiguration.class).value();
            if (scanPackage != null) {
                AnnotationBean annotationBean = new AnnotationBean();
                annotationBean.setPackage(scanPackage);
                return annotationBean;
            }
        }
        return null;
    }
}
