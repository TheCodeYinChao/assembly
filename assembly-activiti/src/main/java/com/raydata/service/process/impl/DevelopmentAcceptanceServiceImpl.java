package com.raydata.service.process.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.raydata.ProjectEnum;
import com.raydata.core.ResultVo;
import com.raydata.core.page.PagePm;
import com.raydata.dao.BusinessProjectMapper;
import com.raydata.model.BusinessProject;
import com.raydata.pojo.development.acceptance.ProjectListReqVo;
import com.raydata.service.process.DevelopmentAcceptanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhouchao
 * @Date 2019/9/11 18:16
 * @Description
 **/
@Service
@Slf4j
public class DevelopmentAcceptanceServiceImpl implements DevelopmentAcceptanceService {

    @Autowired
    private BusinessProjectMapper businessProjectMapper;

    @Override
    public ResultVo getProjectList(ProjectListReqVo projectListReqVo) {

        IPage<BusinessProject> page = new PagePm<>(projectListReqVo.getLimit(), projectListReqVo.getPage());

        IPage<BusinessProject> businessProjectIPage = businessProjectMapper.selectPage(page, new QueryWrapper<BusinessProject>()
                .eq("develop_manager", projectListReqVo.getUserId())
                .eq("project_status",ProjectEnum.ProjectStatusEnum.PASS_APPROVAL.getCode()));

        return ResultVo.success(businessProjectIPage);
    }
}
