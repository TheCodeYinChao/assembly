package com.raydata.core.service;// +----------------------------------------------------------------------

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

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
public interface ActivitiService {

    String startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);//根据流程定义ID启动流程实例

    String startProcessInstanceByKey(String processDefinitionKey,String businessKey, Map<String, Object> variables);//根据流程定义Key和业务Key启动流程实例

    ProcessDefinitionEntity queryProcessDefinitionById(String processDefinitionId);//根据流程定义ID获取流程定义

    ProcessInstance queryProcessInstanceByProcessInstanceId(String processInstanceId);//根据流程实例ID获取流程实例

    String queryProcessInstanceByBusinessKey(String businessKey,String processDefinitionKey);//根据业务KEY获取流程实例ID

    String queryActiveTaskByProcessInstanceId(String processInstanceId,String userId);//根据流程实例ID获取正在运行的任务ID

    List<Task> queryActiveTaskByProcessInstanceId(String processInstanceId);//根据流程实例ID获取正在运行的任务列表

    String queryCandidateGroup(String taskId);//获取当前任务的候选组ID

    Boolean validateActiviti(String instanceId);//根据流程实例ID查询流程实例是否已经停止

    List<Task> queryPersonalTask(String userId);//查询个人的任务列表

    List<Task> queryCandidateGroupTask(String groupId);//查询个人所在候选组的任务列表

    List<Task> queryCandidateUserTask(String userId);//查询个人候选人的任务列表

    List<Task> queryTaskByUser(String userId,int page,int limit);//查询个人代办任务

    long queryTaskCountByUser(String userId);//查询个人代办任务总条数

    void complet(String taskId);//完成任务

    void complet(String taskId, Map<String, Object> variables);//完成任务

    void claim(String taskId, String userId);//认领任务

    void setVariable(String taskId, String variableName, Object value);//根据变量名称修改变量

    void setVariableLocal(String taskId, String variableName, Object value);//根据变量名称修改局部变量

    void setVariables(String taskId, Map<String, ? extends Object> variables);//修改变量

    void setVariablesLocal(String taskId, Map<String, ? extends Object> variables);//修改局部变量

    List<HistoricTaskInstance> getHistoricTaskAssignee(String userId);//获取个人历史任务列表

    List<HistoricTaskInstance> getHistoricTaskFinishedAssignee(String userId);//获取个人已完成历史任务列表

    List<HistoricTaskInstance> getHistoricTaskCandidateUser(String userId);//获取个人候选人历史任务列表

    List<HistoricTaskInstance> getHistoricTaskFinishedCandidateUser(String userId);//获取个人候选人已完成历史任务列表

    List<HistoricTaskInstance> getHistoricTaskCandidateGroup(String groupId);//获取个人所在候选组历史任务列表

    List<HistoricTaskInstance> getHistoricTaskFinishedCandidateGroup(String groupId);//获取个人所在候选组已完成历史任务列表

    List<HistoricTaskInstance> getHistoricTaskInvoledUser(String involedUser);


}
