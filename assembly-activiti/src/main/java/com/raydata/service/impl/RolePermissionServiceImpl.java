package com.raydata.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.raydata.dao.RolePermissionMapper;
import com.raydata.model.PerRolePermission;
import com.raydata.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, PerRolePermission> implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public void delete(Integer roleId) {
        rolePermissionMapper.delete(new UpdateWrapper<PerRolePermission>().eq("role_id",roleId));
    }
}
