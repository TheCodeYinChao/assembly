package com.raydata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.raydata.model.User;

public interface UserMapper extends BaseMapper<User> {
    int getCount();
}
