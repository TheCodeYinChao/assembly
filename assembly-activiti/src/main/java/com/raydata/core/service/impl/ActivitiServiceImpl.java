package com.raydata.core.service.impl;// +----------------------------------------------------------------------

import com.raydata.core.service.ActivitiService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

// | ProjectName: activiti
// +----------------------------------------------------------------------
// | Date: 2019/8/27
// +----------------------------------------------------------------------
// | Time: 11:37
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@Service
@Slf4j
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

    @Override
    public String startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) {
        log.info("【启动工作流】processDefinitionId={},variables={}", processDefinitionId, variables);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        return processInstance.getId();
    }

    @Override
    public String startProcessInstanceByKey(String processDefinitionKey,String businessKey, Map<String, Object> variables) {
        log.info("【启动工作流】processDefinitionKey={},businessKey={},variables={}", processDefinitionKey,businessKey, variables);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey, variables);
        return processInstance.getId();
    }

    @Override
    public ProcessDefinitionEntity queryProcessDefinitionById(String processDefinitionId) {
        log.info("【根据流程定义ID获取流程定义】processDefinitionId={}", processDefinitionId);
        ProcessDefinitionEntity processDefinitionEntity= (ProcessDefinitionEntity) repositoryService
                .getProcessDefinition(processDefinitionId);
        return processDefinitionEntity;
    }

    @Override
    public ProcessInstance queryProcessInstanceByProcessInstanceId(String processInstanceId) {
        log.info("【根据流程实例ID获取流程实例】processInstanceId={}", processInstanceId);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        return processInstance;
    }

    @Override
    public String queryProcessInstanceByBusinessKey(String businessKey,String processDefinitionKey) {
        log.info("【根据业务KEY获取流程实例ID】businessKey={},processDefinitionKey={}", businessKey,processDefinitionKey);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                 .processInstanceBusinessKey(businessKey,processDefinitionKey)
                 .singleResult();
        return processInstance.getId();
    }

    @Override
    public String queryActiveTaskByProcessInstanceId(String processInstanceId,String userId) {
        log.info("【根据流程实例ID获取正在运行的任务ID】processInstanceId={},userId={}", processInstanceId,userId);
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskCandidateOrAssigned(userId)
                .active()
                .singleResult();
        return task.getId();
    }

    @Override
    public List<Task> queryActiveTaskByProcessInstanceId(String processInstanceId) {
        log.info("【根据流程实例ID获取正在运行的任务列表】processInstanceId={}", processInstanceId);
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .active()
                .list();
        return tasks;
    }

    @Override
    public String queryCandidateGroup(String taskId) {
        List identityLinkList = taskService.getIdentityLinksForTask(taskId);
        if (identityLinkList != null && identityLinkList.size() > 0) {
            for (Iterator iterator = identityLinkList.iterator(); iterator.hasNext();) {
                IdentityLink identityLink = (IdentityLink) iterator.next();
                if (identityLink!= null) {
                    return identityLink.getGroupId();
                }
            }
        }
        return null;
    }

    @Override
    public Boolean validateActiviti(String instanceId) {
        log.info("【验证工作流是不是已经停止】instanceId={}", instanceId);
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                            .processInstanceId(instanceId).singleResult();
        if (pi != null) {
            return false;
        }
        return true;
    }

    @Override
    public List<Task> queryPersonalTask(String userId) {
        log.info("【查询个人的任务列表】userId={}",userId);
        List<Task> taskList = taskService.createTaskQuery()
                .taskAssignee(userId)
                .orderByTaskCreateTime().desc()
                .list();
        return taskList;
    }

    @Override
    public List<Task> queryCandidateGroupTask(String groupId) {
        log.info("【查询个人所在候选组的任务列表】groupId={}",groupId);
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateGroup(groupId)
                .orderByTaskCreateTime().desc()
                .list();
        return taskList;
    }

    @Override
    public List<Task> queryCandidateUserTask(String userId) {
        log.info("【查询个人候选人的任务列表】userId={}",userId);
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateUser(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();
        return taskList;
    }

    @Override
    public List<Task> queryTaskByUser(String userId,int page,int limit) {
        log.info("【查询个人代办任务】userId={}",userId);
        /*List<Task> personalTasks = queryPersonalTask(userId);
        List<Task> candidateUserTasks = queryCandidateUserTask(userId);
        List<Task> result = Stream.of(personalTasks, candidateUserTasks)
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());

        List<Task> tasks = result.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()
                -> new TreeSet<>(Comparator.comparing(o
                -> o.getId()))), ArrayList::new));*/
        List<Task> tasks =  taskService.createTaskQuery()
                .taskCandidateOrAssigned(userId)
                .orderByTaskCreateTime()
                .desc()
                .listPage(page,limit);
        return tasks;
    }

    @Override
    public long queryTaskCountByUser(String userId) {
        log.info("【查询个人代办任务总条数】userId={}",userId);
        long count =  taskService.createTaskQuery()
                .taskCandidateOrAssigned(userId)
                .count();
        return count;
    }

    @Override
    public void complet(String taskId) {
        log.info("【完成任务】taskId={}",taskId);
        taskService.complete(taskId);
    }

    @Override
    public void complet(String taskId, Map<String,Object> variables) {
        log.info("【完成任务】taskId={},variables={}",taskId, variables);
        taskService.complete(taskId,variables);
    }

    @Override
    public void claim(String taskId, String userId) {
        log.info("【认领任务】taskId={},userId={}",taskId, userId);
        taskService.claim(taskId,userId);
    }

    @Override
    public void setVariable(String taskId, String variableName, Object value) {
        log.info("【根据变量名称修改变量】taskId={},variableName={},value={}",taskId, variableName,value);
        taskService.setVariable(taskId,variableName,value);
    }

    @Override
    public void setVariableLocal(String taskId, String variableName, Object value) {
        log.info("【根据变量名称修改局部变量】taskId={},variableName={},value={}",taskId, variableName,value);
        taskService.setVariableLocal(taskId,variableName,value);
    }

    @Override
    public void setVariables(String taskId, Map<String, ?> variables) {
        log.info("【修改变量】taskId={},variables={}",taskId, variables);
        taskService.setVariables(taskId,variables);
    }

    @Override
    public void setVariablesLocal(String taskId, Map<String, ?> variables) {
        log.info("【修改局部变量】taskId={},variables={}",taskId, variables);
        taskService.setVariablesLocal(taskId,variables);
    }

    @Override
    public List<HistoricTaskInstance> getHistoricTaskAssignee(String userId) {
        log.info("【获取个人历史任务列表】userId={}",userId);
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .orderByHistoricTaskInstanceStartTime()
                .desc()
                .list();
        return list;
    }

    @Override
    public List<HistoricTaskInstance> getHistoricTaskFinishedAssignee(String userId) {
        log.info("【获取个人历史完成任务列表】userId={}",userId);
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        return list;
    }

    @Override
    public List<HistoricTaskInstance> getHistoricTaskCandidateUser(String userId) {
        log.info("【获取个人候选人历史任务列表】userId={}",userId);
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskCandidateUser(userId)
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        return list;
    }

    @Override
    public List<HistoricTaskInstance> getHistoricTaskFinishedCandidateUser(String userId) {
        log.info("【获取个人候选人已完成历史任务列表】userId={}",userId);
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskCandidateUser(userId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        return list;
    }

    @Override
    public List<HistoricTaskInstance> getHistoricTaskCandidateGroup(String groupId) {
        log.info("【获取个人所在候选组历史任务列表】groupId={}",groupId);
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskCandidateGroup(groupId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        return list;
    }

    @Override
    public List<HistoricTaskInstance> getHistoricTaskFinishedCandidateGroup(String groupId) {
        log.info("【获取个人所在候选组已完成历史任务列表】groupId={}",groupId);
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskCandidateGroup(groupId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        return list;
    }

    @Override
    public List<HistoricTaskInstance> getHistoricTaskInvoledUser(String involedUser) {
        log.info("【获取个人参与任务历史任务列表】involedUser={}",involedUser);
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskInvolvedUser(involedUser)
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        return list;
    }
}
