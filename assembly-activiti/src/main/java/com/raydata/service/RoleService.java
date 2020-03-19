package com.raydata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.raydata.model.Role;

public interface RoleService extends IService<Role> {
    Object insertRole(String param);

    Object updateRole(String param);

    Object selectRoleById(Integer roleId);

    Object selectRolesPage(String param, IPage page);
}
