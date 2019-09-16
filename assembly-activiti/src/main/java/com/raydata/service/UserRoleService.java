package com.raydata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.raydata.model.UserRole;

public interface UserRoleService extends IService<UserRole> {
    public void delete(Integer userId);
}
