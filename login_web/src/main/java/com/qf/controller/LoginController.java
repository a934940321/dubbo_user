package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.User;
import com.qf.service.IEmailService;
import com.qf.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequestMapping("user")
public class LoginController {

    private String uuid = null;
    private User users;

    @Reference
    private IUserService userService;
    @Reference
    private IEmailService emailService;

    Timer timer = new Timer();
    //跳转登录页面
    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }

    //判断用户名密码是否正确
    @RequestMapping("isOk")
    public String isOk(User user, Model model){
        boolean ok = userService.isOk(user);
        if (ok){
            return "ok";
        }else {
            model.addAttribute("msg","登录失败");
        }
        return "login";
    }

    //忘记密码
    @RequestMapping("toForget")
    public String toForget(){
        return "forget";
    }

    //发送找回密码邮件
    @RequestMapping("forget")
    public String forget(String email, Model model, HttpSession session){
        users = userService.findUserByEmail(email);

        String s = emailService.sendForget(email);
        if (s != ""){
            uuid = s;
            //定时器
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    uuid = "dj3qh2iufqiwf";
                }
            },1000*60);
            return "redirect:/user/toSucc";
        }

        model.addAttribute("msg","发送失败，请重新发送");
        return "forget";
    }

    @RequestMapping("toSucc")
    public String toSucc(){
        return "succ";
    }

    //邮件改密码地址
    @RequestMapping("changePassword/{uuids}")
    public String changePassword(@PathVariable String uuids){
        if (uuid.equals(uuids)){

            return "change";
        }

        return "error";
    }

    //用户修改密码
    @RequestMapping("userChange")
    public String userChange(String password){
        users.setPassword(password);
        boolean b = userService.updataUser(users);
        if (b){
            return "login";
        }
        return "error";

    }


}
