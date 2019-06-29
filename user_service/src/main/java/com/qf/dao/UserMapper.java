package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.User;


public interface UserMapper extends BaseMapper<User> {

     User isOk(User user);

     User isExist(String username);

     User findUserByEmail(String eamil);


}
