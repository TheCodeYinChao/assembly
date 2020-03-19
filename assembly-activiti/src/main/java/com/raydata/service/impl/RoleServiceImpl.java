package com.raydata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.raydata.core.ResultVo;
import com.raydata.dao.RoleMapper;
import com.raydata.dao.RolePermissionMapper;
import com.raydata.model.PerPermission;
import com.raydata.model.PerRolePermission;
import com.raydata.model.Role;
import com.raydata.model.UserRole;
import com.raydata.service.PermissionService;
import com.raydata.service.RolePermissionService;
import com.raydata.service.RoleService;
import com.raydata.util.Assert;
import com.raydata.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;


    @Override
    @Transactional
    public Object insertRole(String param) {
        Role role = JacksonUtil.toObj(param, Role.class);
        Assert.paramsNotNullValid(role,"roleName","roleCreater");
        role.setCreateTime(new Date());
        roleMapper.insert(role);

        addRolePer(param,role);
        return ResultVo.success();
    }

    /**
     * 角色赋权
     * @param param
     * @param role
     */
    private void addRolePer(String param, Role role) {
        Map<String,Object> map = JacksonUtil.toObj(param, Map.class);
        Object perIds = map.get("perIds");
        if(Objects.isNull(perIds)) return;

        List<String> rIds = Splitter.on(",").trimResults().splitToList(perIds.toString());
        List<PerRolePermission> rolePers = new ArrayList<>();
        rolePermissionService.delete(role.getRoleId());
        for(String rId:rIds){
            PerRolePermission rp = new PerRolePermission();
            rp.setPerId(Integer.valueOf(rId));
            rp.setRoleId(role.getRoleId());
            rolePers.add(rp);
        }
        rolePermissionService.saveBatch(rolePers,rolePers.size());
    }

    @Override
    @Transactional
    public Object updateRole(String param) {
        Role role = JacksonUtil.toObj(param, Role.class);
        Assert.objNotNull(role.getRoleId(),"roleId");
        roleMapper.updateById(role);
        addRolePer(param, role);
        return ResultVo.success();
    }

    @Override
    public Object selectRoleById(Integer roleId) {
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("role_id", roleId));
        List<PerRolePermission> perIds = rolePermissionService.getBaseMapper().selectList(new QueryWrapper<PerRolePermission>().eq("role_id", roleId));
        if(!CollectionUtils.isEmpty(perIds)){
            List<PerPermission> pers = permissionService.getBaseMapper().selectList(new QueryWrapper<PerPermission>().eq("per_id", perIds));
            role.setPers(pers);
        }
        return ResultVo.success(role);
    }

    @Override
    public Object selectRolesPage(String param, IPage page) {
        Role role = JacksonUtil.toObj(param, Role.class);
        page = roleMapper.selectPage(page, new QueryWrapper<Role>()
                                            .eq(false,"role_id",role.getRoleId())
                                            .like(false,"role_name",role.getRoleName())
                                            .eq(false,"system_flag",role.getSystemFlag())
                                            .eq(false,"role_creater",role.getRoleCreater())
                                            .eq("role_status",0)
                                    );

        List<Role> records = page.getRecords();
        records.forEach(x->{
            Integer roleId = x.getRoleId();
            List<PerRolePermission> perIds = rolePermissionService.getBaseMapper().selectList(new QueryWrapper<PerRolePermission>().eq("role_id", roleId));
            if(!CollectionUtils.isEmpty(perIds)){
                List<PerPermission> pers = permissionService.getBaseMapper().selectList(new QueryWrapper<PerPermission>().eq("per_id", perIds));
                x.setPers(pers);
            }
        });

        return ResultVo.success(page);
    }
}
