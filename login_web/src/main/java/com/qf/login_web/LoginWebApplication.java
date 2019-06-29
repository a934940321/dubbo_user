package com.qf.login_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class LoginWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginWebApplication.class, args);
    }

}
