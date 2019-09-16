package com.raydata.service.process.impl;// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.raydata.common.Constant;
import com.raydata.core.service.ActivitiService;
import com.raydata.dao.BusinessProjectMapper;
import com.raydata.model.BusinessForm;
import com.raydata.model.BusinessProject;
import com.raydata.pojo.process.BusinessProjectReqVo;
import com.raydata.pojo.process.FormVo;
import com.raydata.service.process.BusinessProjectService;
import com.raydata.service.process.FormService;
import com.raydata.util.UUIDUtil;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/11
// +----------------------------------------------------------------------
// | Time: 16:18
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@Service
public class BusinessProjectServiceImpl implements BusinessProjectService {

    @Autowired
    private BusinessProjectMapper businessProjectMapper;

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private FormService formService;

    @Value("${process.processInstanceKey.projectConfirm}")
    private String projectConfirm;

    @Override
    @Transactional
    public int addProject(BusinessProjectReqVo businessProjectReq) {
        BusinessProject businessProject = new BusinessProject();
        BeanUtils.copyProperties(businessProjectReq, businessProject);
        String businessKey = UUIDUtil.getUUID();
        businessProject.setProjectId(businessKey);
        businessProject.setStatus(Constant.ALLOW_STATUS);
        long time =  System.currentTimeMillis();
        businessProject.setCreateBy(businessProjectReq.getUserId());
        businessProject.setCreateTime(time);
        businessProject.setUpdateTime(time);
        int i = businessProjectMapper.insert(businessProject);
        if(i>0){
            Map variables = new HashMap();
            variables.put("applicant",businessProjectReq.getUserId());
            String processInstanceId = activitiService.startProcessInstanceByKey(projectConfirm,businessKey, variables);
            String taskId = activitiService.queryActiveTaskByProcessInstanceId(processInstanceId,businessProjectReq.getUserId());
            activitiService.complet(taskId,variables);
        }
        return i;
    }

    @Override
    public int uploadPrototype(FormVo formVo) {
        int i = formService.addForm(formVo);
        if(i>0){
            BusinessForm form = formVo.getForm();

            //完成任务
            Map variables = new HashMap();
            variables.put("gateway","first");

            String taskId = form.getTaskId();
            activitiService.complet(taskId,variables);

            //下一个任务拾取
            String processInstanceId = activitiService.queryProcessInstanceByBusinessKey(form.getProjectId(), projectConfirm);
            List<Task> tasks = activitiService.queryActiveTaskByProcessInstanceId(processInstanceId);
            for(Task task:tasks){
                String groupId = activitiService.queryCandidateGroup(task.getId());
                String userId = "2";//根据角色，项目区域和行业获取行业负责人或区域经理
                activitiService.claim(task.getId(),userId);
            }
        }
        return i;
    }

    @Override
    public int assign(FormVo formVo) {
        int i = formService.addForm(formVo);
        if(i>0) {
            BusinessForm form = formVo.getForm();
            String handler = formVo.getHandler();
            //完成任务
            Map variables = new HashMap();
            variables.put("develop", handler);

            String taskId = form.getTaskId();
            activitiService.complet(taskId, variables);
        }
        return i;
    }

    @Override
    public int check(FormVo formVo) {
        int i = formService.addForm(formVo);
        if(i>0) {
            Map variables = new HashMap();
            BusinessForm form = formVo.getForm();
            if(form.getOperationId()==1){
                variables.put("devManagerApprove", "y");
            }else if(form.getOperationId()==2){
                variables.put("devManagerApprove", "n");
            }
            String taskId = form.getTaskId();
            activitiService.complet(taskId, variables);
        }
        return i;
    }

}
