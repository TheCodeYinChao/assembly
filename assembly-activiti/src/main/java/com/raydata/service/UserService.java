package com.raydata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.raydata.model.User;

public interface UserService {
    public Object getByUserId(Integer userId);

    Object insertUser(String params);

    Object getUsers(String param,IPage page);

    Object updateUser(String params);

    Object selectUserPer(Integer userId);
}
