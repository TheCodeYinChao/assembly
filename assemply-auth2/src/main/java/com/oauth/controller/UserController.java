package com.oauth.controller;

import com.oauth.dao.UserRepositoty;
import com.oauth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Admin on 2019/6/8.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepositoty  userRepositoty;    // 账户数据操作

    /**
     * 初始化用户数据
     */
    @Autowired
    public void init(){

        // 为了方便测试,这里添加了两个不同角色的账户
        userRepositoty.deleteAll();

        User accountA = new User();
        accountA.setName("admin");
        accountA.setPwd("admin");
//        accountA.setRoles(new String[]{"ROLE_ADMIN","ROLE_USER"});
        userRepositoty.save(accountA);

        User accountB = new User();
        accountB.setName("guest");
        accountB.setPwd("pass123");
//        accountB.setRoles(new String[]{"ROLE_GUEST"});
        userRepositoty.save(accountB);
    }

    /**
     * 获取授权用户的信息
     * @param user 当前用户
     * @return 授权信息
     */
    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
