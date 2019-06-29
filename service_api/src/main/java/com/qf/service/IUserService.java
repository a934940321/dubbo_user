package com.qf.service;

import com.qf.entity.User;

public interface IUserService {
    //判断用户登入是否正确
    boolean isOk (User user);
    //判断用户名是否存在
    boolean isExist(String username);
    //添加用户
    boolean addUser(User user);
    //通过邮箱获取用户id
    User findUserByEmail(String email);
    //修改用户
    boolean updataUser(User user);
}
