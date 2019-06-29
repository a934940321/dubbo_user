package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.domain.UserDomain;
import com.qf.entity.User;
import com.qf.service.IEmailService;
import com.qf.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class RegisterController {

    @Reference
    private IUserService userService;
    @Reference
    private IEmailService emailService;

    //跳转注册页面
    @RequestMapping("toRegister")
    public String toRegister(){
        return "register";
    }

    //判断用户名是否存在
    @RequestMapping("isExist")
    public void isExist(String username, HttpServletResponse response){
        boolean exist = userService.isExist(username);
        if (exist){
            try {
                response.getWriter().write("1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                response.getWriter().write("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //判断邮箱是否存在
    @RequestMapping("emailIsExist")
    public void emailIsExist(String email,HttpServletResponse response){
        User userByEmail = userService.findUserByEmail(email);
        if (userByEmail != null){
            try {
                response.getWriter().write("1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                response.getWriter().write("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //发送邮件验证码
    @RequestMapping("email")
    public void email(String email, HttpServletResponse response, HttpSession session){
        String ecode = emailService.sendEmail(email);

        if (ecode != null || ecode != ""){
            try {
                session.setAttribute("ecode",ecode);
                response.getWriter().write("1");
            } catch (IOException e) {

            }
        }else {
            try {
                response.getWriter().write("0");
            } catch (IOException e) {

            }
        }
    }

    //用户注册
    @RequestMapping("userRegister")
    public String userRegister(User user){

        boolean b = userService.addUser(user);
        System.out.println(b);
        if (b){
            return "redirect:http://localhost:8080/user/toLogin";
        }
        return "error";
    }

    //判断验证码是否正确
    @RequestMapping("isCode")
    public void isCode(String code,HttpSession session,HttpServletResponse response){
        String code2 = (String) session.getAttribute("ecode");
//        String code2 = "123";
        if(code.equals(code2)){
            try {
                response.getWriter().write("1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                response.getWriter().write("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
