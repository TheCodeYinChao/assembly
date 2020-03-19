package com.raydata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.raydata.model.PerPermission;

public interface PermissionService extends IService<PerPermission> {

    Object selectPerPage(String param, IPage page);

    Object saveUpdate(String param);

    Object selectAllPer();
}
