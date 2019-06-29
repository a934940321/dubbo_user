package com.qf.email_service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceApplicationTests {

    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            System.out.println(random.nextInt(9000)+1000);
        }

    }

    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    public void test1(){
        try {
        //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //创建一个Spring提供的邮件帮助对象,true表示为multipart邮件(附件)
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
        //设置发送方
        messageHelper.setFrom("");

        //设置接收方
        //to - 普通接收方
        //cc - 抄送方
        //bcc - 密送方
        messageHelper.addTo("","会员");

        //设置标题
        messageHelper.setSubject("找回密码");

        //设置内容
        messageHelper.setText("尊敬的用户，您的验证码为："+1234);

        //设置时间
        messageHelper.setSentDate(new Date());

        //发送附件
//            messageHelper.addAttachment("a.jpg", new File("C:\\Users\\ken\\Pictures\\Saved Pictures\\奥格瑞玛.jpg"));
        //发送邮件
        javaMailSender.send(mimeMessage);

    } catch ( MessagingException e) {

    } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
