package com.activiti.service;

import com.activiti.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created by Admin on 2019/9/14.
 */
public interface UserService extends  IService<User>{
    public String testMapper();

    public Object getUser();
}
