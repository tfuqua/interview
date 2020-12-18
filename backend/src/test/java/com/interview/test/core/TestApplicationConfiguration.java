package com.interview.test.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.interview")
public class TestApplicationConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(TestApplicationConfiguration.class, args);
    }

}
