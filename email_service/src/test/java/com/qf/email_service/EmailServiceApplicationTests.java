package com.qf.email_service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceApplicationTests {

    @Reference
    private IUserService userService;

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
        messageHelper.setFrom("a934940321@sina.com");

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


    @Test
    public void test2() throws MessagingException, IOException {
        // 准备连接服务器的会话信息
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", "pop.sina.com");

        // 创建Session实例对象
        Session session = Session.getInstance(props);

        // 创建IMAP协议的Store对象
        Store store = session.getStore("pop3");

        // 连接邮件服务器
        store.connect("a934940321@sina.com", "a934940321!");

        // 获得收件箱
        Folder folder = store.getFolder("INBOX");
        // 以读写模式打开收件箱
        folder.open(Folder.READ_WRITE);

        // 获得收件箱的邮件列表
        Message[] messages = folder.getMessages();

        // 打印不同状态的邮件数量
        System.out.println("收件箱中共" + messages.length + "封邮件!");
        System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
        System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
        System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");

        System.out.println("------------------------开始解析邮件----------------------------------");


        int total = folder.getMessageCount();
        System.out.println("-----------------您的邮箱共有邮件：" + total + " 封--------------");
        // 得到收件箱文件夹信息，获取邮件列表
        Message[] msgs = folder.getMessages();
        System.out.println("\t收件箱的总邮件数：" + msgs.length);
        for (int i = 0; i < total; i++) {
            Message a = msgs[i];
            //   获取邮箱邮件名字及时间

            System.out.println(a.getReplyTo());
            System.out.println("发送时间：" + a.getSentDate());
            System.out.println("主题：" + a.getSubject());
            System.out.println("内容：" + a.getContent());

            System.out.println("==============");
//                System.out.println(a.getSubject() + "   接收时间：" + a.getReceivedDate().toLocaleString()+"  contentType()" +a.getContentType());
        }
        System.out.println("\t未读邮件数：" + folder.getUnreadMessageCount());
        System.out.println("\t新邮件数：" + folder.getNewMessageCount());
        System.out.println("----------------End------------------");



        // 关闭资源
        folder.close(false);
        store.close();
    }


    @Test
    public void demo() throws MessagingException, IOException {
        // 准备连接服务器的会话信息
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", "pop.sina.com");

        // 创建Session实例对象
        Session session = Session.getInstance(props);

        // 创建IMAP协议的Store对象
        Store store = session.getStore("pop3");

        // 连接邮件服务器
        store.connect("a934940321@sina.com", "a934940321!");
//..........................................................................
        // 获得收件箱
        Folder folder = store.getFolder("INBOX");
        // 以读写模式打开收件箱
        folder.open(Folder.READ_WRITE);

        // 获得收件箱的邮件列表
        Message[] messages = folder.getMessages();

        int total = folder.getMessageCount();
        System.out.println("-----------------您的邮箱共有邮件：" + total + " 封--------------");
        // 得到收件箱文件夹信息，获取邮件列表
        Message[] msgs = folder.getMessages();
        System.out.println("\t收件箱的总邮件数：" + msgs.length);
        for (int i = 0; i < total; i++) {
            Message a = msgs[i];
            //   获取邮箱邮件名字及时间
//            System.out.println(a.getFrom()[0].toString().split("<")[1]);
            Flags flags = a.getFlags();
            if (flags.contains(Flags.Flag.SEEN)){
                //已读
            }else {
                //未读
                String email = a.getFrom()[0].toString().split("<")[1].split(">")[0];
                String title = a.getSubject();
                System.out.println(email);
                System.out.println("主题：" + title);
                User user = userService.findUserByEmail(email);
                user.setPassword(title);
                boolean b = userService.updataUser(user);
                if (b){
                    a.setFlag(Flags.Flag.DELETED,true);
                }


            }

        }

        // 关闭资源
        folder.close(true);
        store.close();
    }


}
