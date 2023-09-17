package com.odde.atddv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CucumberConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(CucumberConfiguration.class, args);
    }
}

