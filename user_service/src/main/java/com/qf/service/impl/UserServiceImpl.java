package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    //判断用户登入是否正确
    @Override
    public boolean isOk(User user) {
        User user1 = userMapper.isOk(user);
        /*userMapper.isOk()*/
        if (user1 != null){
            return true;
        }
        return false;
    }

    //判断用户名是否正确
    @Override
    public boolean isExist(String username) {
        User user = userMapper.isExist(username);
        if (user != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(User user) {
        int insert = userMapper.insert(user);
        if (insert > 0){
            return true;
        }
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userMapper.findUserByEmail(email);
        return user;
    }

    @Override
    public boolean updataUser(User user) {
        Integer integer = userMapper.updateById(user);
        if (integer > 0){
            return true;
        }
        return false;
    }


}
