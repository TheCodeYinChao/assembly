package com.oauth.service;

import com.oauth.dao.UserRepositoty;
import com.oauth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2019/6/8.
 */
@Service
public class UserService  implements UserDetailsService{
    @Autowired
    private UserRepositoty userRepositoty;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User admin = userRepositoty.findByName("admin");

        if(admin!=null){
            return new org.springframework.security.core.userdetails.User(admin.getName(),admin.getPwd(),null);
        }
        throw new UsernameNotFoundException("用户名不存在");
    }
}
