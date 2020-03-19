package com.raydata.service.process;// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.raydata.model.BusinessProject;
import com.raydata.pojo.process.TaskVo;

import java.util.List;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/11
// +----------------------------------------------------------------------
// | Time: 19:32
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
public interface ProjectService {

    IPage unfinishedTask(String userId, int page, int limits);//分页获取个人待办任务列表

    BusinessProject getProjectBase(String projectId);//获取项目详情
}
