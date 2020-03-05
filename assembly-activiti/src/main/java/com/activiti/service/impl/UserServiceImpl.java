package com.activiti.service.impl;

import com.activiti.dao.UserMapper;
import com.activiti.model.User;
import com.activiti.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2019/9/14.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    public String testMapper(){
        User user = userMapper.selectById(1);
        return user.toString();
    }

    @Override
    public Object getUser() {
        return userMapper.selectList(new QueryWrapper<User>());
    }
}
