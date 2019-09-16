package com.raydata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.raydata.model.PerRolePermission;
import com.raydata.model.UserRole;

public interface RolePermissionService extends IService<PerRolePermission> {
    public void delete(Integer roleId);
}
