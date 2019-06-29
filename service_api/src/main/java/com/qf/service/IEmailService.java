package com.qf.service;

public interface IEmailService {
    //发送邮件
    String sendEmail(String email);
    //发送找回密码邮件
    String sendForget(String email);
}
