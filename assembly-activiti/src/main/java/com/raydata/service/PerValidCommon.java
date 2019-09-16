package com.raydata.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.raydata.model.PerPermission;
import com.raydata.model.PerRolePermission;
import com.raydata.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PerValidCommon {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;


    public boolean valid(String url,String userId){
        List<UserRole> roles = userRoleService.getBaseMapper().selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
        boolean  rs = false;
        if(!CollectionUtils.isEmpty(roles)){
            List<Integer> roleIds = Lists.newArrayList();
            roles.forEach(x->{
                roleIds.add(x.getRoleId());
            });
            if(!CollectionUtils.isEmpty(roleIds)){
                List<PerRolePermission> pers = rolePermissionService.getBaseMapper().selectList(new QueryWrapper<PerRolePermission>().in("role_id", roleIds));
                if(!CollectionUtils.isEmpty(pers)){
                    List<Integer> perIds = Lists.newArrayList();
                    pers.forEach(y->{
                        perIds.add(y.getPerId());
                    });
                    List<PerPermission> permissions = permissionService.getBaseMapper().selectList(new QueryWrapper<PerPermission>().in("per_id", perIds));
                    for (PerPermission permission : permissions) {
                        rs = url.contains(permission.getPerUrl());
                        break;
                    }

                }
            }
        }
        return rs;
    }

    public boolean valid(Integer perId,String userId){
        boolean rs = false;
        List<UserRole> roles = userRoleService.getBaseMapper().selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
        if(!CollectionUtils.isEmpty(roles)){
            List<Integer> roleIds = Lists.newArrayList();
            roles.forEach(x->{
                roleIds.add(x.getRoleId());
            });
            if(!CollectionUtils.isEmpty(roleIds)){
                List<PerRolePermission> pers = rolePermissionService.getBaseMapper().selectList(new QueryWrapper<PerRolePermission>().in("role_id", roleIds));
                if(!CollectionUtils.isEmpty(pers)){
                    List<Integer> perIds = Lists.newArrayList();
                    pers.forEach(y->{
                        perIds.add(y.getPerId());
                    });
                    if(perIds.contains(perId)){
                        rs = true;
                    }
                }
            }
        }
        return rs;
    }
}
