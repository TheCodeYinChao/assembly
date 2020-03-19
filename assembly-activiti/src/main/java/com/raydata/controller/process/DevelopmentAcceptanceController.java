package com.raydata.controller.process;

import com.raydata.core.ResultVo;
import com.raydata.pojo.development.acceptance.ProjectListReqVo;
import com.raydata.service.process.DevelopmentAcceptanceService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhouchao
 * @Date 2019/9/10 18:51
 * @Description
 **/
@RestController
@RequestMapping("development/acceptance")
@Slf4j
public class DevelopmentAcceptanceController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private DevelopmentAcceptanceService developmentAcceptanceService;

    /**
     * 根据userid，查询开发经理的项目列表
     *
     * @param projectListReqVo {@link ProjectListReqVo}
     * @return
     */
    @PostMapping("/prject/list")
    public ResultVo getProjectList(@RequestBody ProjectListReqVo projectListReqVo) {

        return developmentAcceptanceService.getProjectList(projectListReqVo);
    }


}
