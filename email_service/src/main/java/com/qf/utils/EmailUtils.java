package com.qf.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

public class EmailUtils {

    @Autowired
    private static JavaMailSender javaMailSender;

    //生成4位数验证码
    public static String ecode(){
        Random random = new Random();
       int i = random.nextInt(9000)+1000;
       String ecode = i+"";
        return ecode;
    }

    //发送邮箱验证
    public static boolean sendEcode(String email){

        try {
            //创建一封邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //创建一个Spring提供的邮件帮助对象,true表示为multipart邮件(附件)
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            //设置发送方
            messageHelper.setFrom("a934940321@sina.com");

            //设置接收方
            //to - 普通接收方
            //cc - 抄送方
            //bcc - 密送方
            messageHelper.addTo("934940321@qq.com","会员");

            //设置标题
            messageHelper.setSubject("找回密码");

            //设置内容
            messageHelper.setText("尊敬的用户，您的验证码为："+EmailUtils.ecode());

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
        return true;
    }

}
