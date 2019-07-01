package com.qf.email_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.qf.utils.EmailUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;

@SpringBootApplication(scanBasePackages = "com.qf")
@DubboComponentScan("com.qf")
public class EmailServiceApplication {

    public static void main(String[] args) throws MessagingException {
        SpringApplication.run(EmailServiceApplication.class, args);


  }

}
