package org.mvnsearch;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.mvnsearch.uic.UicTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("/uic-dubbo-consumer.xml")
public class SpringBootDubboClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboClientApplication.class, args);
    }

    /*@Bean
    public ReferenceBean uicTemplate() {
        ReferenceBean<UicTemplate> referenceBean = new ReferenceBean<UicTemplate>();
        referenceBean.setInterface(UicTemplate.class);
        referenceBean.setId("uicTemplate");
        referenceBean.setTimeout(10000);
        return referenceBean;
    }*/
}
