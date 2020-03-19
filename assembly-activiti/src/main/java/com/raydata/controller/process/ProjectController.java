package com.raydata.controller.process;// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.raydata.core.ResultVo;
import com.raydata.pojo.process.PageReqVo;
import com.raydata.service.process.ProjectService;
import com.raydata.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/11
// +----------------------------------------------------------------------
// | Time: 19:31
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * @description: 分页获取个人代办任务列表
     *
     * @param: 
     * @return: 
     * @author: haiying.qin <haiying.qin@raykite.com>
     * @createTime: 2019/9/12 14:13
     */
    
    @PostMapping("/task/unfinished")
    public Object unfinishedTask(@RequestBody String params){
        PageReqVo pageReqVo = JacksonUtil.toObj(params, PageReqVo.class);
        IPage iPage = projectService.unfinishedTask(pageReqVo.getUserId(), pageReqVo.getPage(), pageReqVo.getLimit());
        return ResultVo.success(iPage);
    }
}
