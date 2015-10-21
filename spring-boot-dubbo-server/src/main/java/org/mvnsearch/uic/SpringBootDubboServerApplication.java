package org.mvnsearch.uic;

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDubboConfiguration("org.mvnsearch.uic")
public class SpringBootDubboServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboServerApplication.class, args);
    }
}
