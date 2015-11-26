package org.mvnsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class SpringBootDubboClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboClientApplication.class, args);
    }
}
