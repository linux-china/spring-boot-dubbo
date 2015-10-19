package org.mvnsearch.uic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("/uic-dubbo-provider.xml")
public class SpringBootDubboServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboServerApplication.class, args);
    }
}
