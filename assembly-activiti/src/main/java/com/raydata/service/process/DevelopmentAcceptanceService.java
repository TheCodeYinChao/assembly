package com.raydata.service.process;

import com.raydata.core.ResultVo;
import com.raydata.pojo.development.acceptance.ProjectListReqVo;

public interface DevelopmentAcceptanceService {
    ResultVo getProjectList(ProjectListReqVo projectListReqVo);

}
