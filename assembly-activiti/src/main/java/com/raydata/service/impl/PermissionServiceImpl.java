package com.raydata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.raydata.core.ResultVo;
import com.raydata.dao.PermissionMapper;
import com.raydata.model.PerPermission;
import com.raydata.model.PerRolePermission;
import com.raydata.service.PermissionService;
import com.raydata.util.Assert;
import com.raydata.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,PerPermission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;



    @Override
    @Transactional
    public Object saveUpdate(String param) {
        PerPermission perPermission = JacksonUtil.toObj(param, PerPermission.class);
        Assert.objNotNull(perPermission.getCreator(),"creator");
        if(Objects.isNull(perPermission.getPerId())){
            Assert.paramsNotNullValid(perPermission,"perPid","perName","perUrl","perType");
            perPermission.setCreateTime(new Date());
        }
        saveOrUpdate(perPermission);
        return ResultVo.success();
    }

    @Override
    public Object selectAllPer() {
        List<PerPermission> perPermissions = permissionMapper.selectList(null);
        return ResultVo.success(perPermissions);
    }

    @Override
    public Object selectPerPage(String param, IPage page) {
        PerPermission perPermission = JacksonUtil.toObj(param, PerPermission.class);
        page = permissionMapper.selectPage(page, new QueryWrapper<PerPermission>()
                                        .eq(false,"per_id",perPermission.getPerId())
                                        .eq(false,"per_pid",perPermission.getPerPid())
                                        .eq(false,"per_name",perPermission.getPerName())
                                        .eq(false,"creator",perPermission.getCreator())
                                        .eq("per_status",0)
                                );
        return ResultVo.success(page);
    }
}
