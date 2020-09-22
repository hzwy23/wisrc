package com.wisrc.zuul.basic;

import com.wisrc.zuul.config.FeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(
        basePackages = {"com.wisrc"}
)
//@EnableEurekaClient
@EnableFeignClients(
        basePackages = {"com.wisrc"}, defaultConfiguration = FeignConfiguration.class
)
public class BasicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }
}
