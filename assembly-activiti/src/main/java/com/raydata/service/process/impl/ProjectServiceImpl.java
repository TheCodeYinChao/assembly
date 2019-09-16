package com.raydata.service.process.impl;// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.raydata.core.page.PagePm;
import com.raydata.core.service.ActivitiService;
import com.raydata.dao.BusinessProjectMapper;
import com.raydata.model.BusinessProject;
import com.raydata.pojo.process.TaskVo;
import com.raydata.service.process.BusinessProjectService;
import com.raydata.service.process.ProjectService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/11
// +----------------------------------------------------------------------
// | Time: 19:36
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private BusinessProjectMapper businessProjectMapper;

    @Override
    public IPage unfinishedTask(String userId, int page, int limit) {
        List<TaskVo> taskVos = new ArrayList<>();
        List<Task> tasks = activitiService.queryTaskByUser(userId,(page-1)*limit,limit);
        long count = activitiService.queryTaskCountByUser(userId);
        IPage iPage = new PagePm(page,limit,count);
        iPage.setRecords(taskVos);

        if(tasks == null || tasks.size() == 0){
            return iPage;
        }
        for(Task task : tasks){
            ProcessInstance processInstance = activitiService.queryProcessInstanceByProcessInstanceId(task.getProcessInstanceId());
            String businessKey = processInstance.getBusinessKey();
            BusinessProject projectBase = getProjectBase(businessKey);
            TaskVo taskVo = new TaskVo();
            BeanUtils.copyProperties(projectBase, taskVo);
            taskVo.setTaskId(task.getId());
            taskVos.add(taskVo);
        }
        return iPage;
    }


    @Override
    public BusinessProject getProjectBase(String projectId) {
        BusinessProject businessProject = businessProjectMapper.selectOne(
                new QueryWrapper<BusinessProject>()
                        .eq("project_id", projectId)
                        .eq("status", 10001));
        return businessProject;
    }
}
