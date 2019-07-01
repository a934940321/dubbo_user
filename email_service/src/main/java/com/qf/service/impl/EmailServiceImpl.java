package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.service.IEmailService;
import com.qf.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.UUID;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public String sendEmail(String email) {

        String ecode = EmailUtils.ecode();
        try {
            //创建一封邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //创建一个Spring提供的邮件帮助对象,true表示为multipart邮件(附件)
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            //设置发送方
            messageHelper.setFrom("a934940321@sina.com");

            //设置接收方
            //to - 普通接收方
            //cc - 抄送方
            //bcc - 密送方
            messageHelper.addTo(email);

            //设置标题
            messageHelper.setSubject("注册账号");

            //设置内容
            messageHelper.setText("尊敬的用户，您的验证码为：" + ecode);

            //设置时间
            messageHelper.setSentDate(new Date());

            //发送附件
//            messageHelper.addAttachment("a.jpg", new File("C:\\Users\\ken\\Pictures\\Saved Pictures\\奥格瑞玛.jpg"));
            //发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
          //  e.printStackTrace();
            return "";
        }
        return ecode;
    }

    @Override
    public String sendForget(String email) {

        String uuid = UUID.randomUUID().toString();

        try {
            //创建一封邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //创建一个Spring提供的邮件帮助对象,true表示为multipart邮件(附件)
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            //设置发送方
            messageHelper.setFrom("a934940321@sina.com");

            //设置接收方
            //to - 普通接收方
            //cc - 抄送方
            //bcc - 密送方
            messageHelper.addTo(email);

            //设置标题
            messageHelper.setSubject("找回密码");

            //设置内容
            messageHelper.setText("尊敬的用户，请点击<a href='http://localhost:8080/user/changePassword/"+uuid+"'>这里</a>找回密码~,或者点击下方链接：http://localhost:8080/user/changePassword/"+uuid,true);

            //设置时间
            messageHelper.setSentDate(new Date());

            //发送附件
//            messageHelper.addAttachment("a.jpg", new File("C:\\Users\\ken\\Pictures\\Saved Pictures\\奥格瑞玛.jpg"));
            //发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            //  e.printStackTrace();
            return "";
        }
        return uuid;
    }



}
