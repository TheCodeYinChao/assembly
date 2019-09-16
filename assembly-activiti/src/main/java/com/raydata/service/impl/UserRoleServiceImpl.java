package com.raydata.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.raydata.dao.UserRoleMapper;
import com.raydata.model.UserRole;
import com.raydata.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public void delete(Integer userId){
        userRoleMapper.delete(new UpdateWrapper<UserRole>().eq("user_id",userId));
    }
}
