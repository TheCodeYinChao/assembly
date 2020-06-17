package com.raydata;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ActivitiTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Ignore
    public void CreateAcceptance() {
        Deployment deployment = repositoryService.createDeployment()
                .key("application_deployment")
                .name("申请部署")
                .addClasspathResource("process/development_acceptance.bpmn")
                .deploy();

        System.out.printf("部署id：%s \n", deployment.getId());
        System.out.printf("部署名称：%s \n", deployment.getName());
        System.out.printf("部署时间：%1$tF %1$tT \n", deployment.getDeploymentTime());

    }

    @Ignore
    public void startProcess() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("application_deployment");

        System.out.println("流程实例id:" + pi.getId());//流程实例id
        System.out.println("流程定义id:" + pi.getProcessDefinitionId());//输出流程定义的id
    }

    @Test
    public void setAssignee(){
        Task task = taskService.createTaskQuery().processInstanceId("17501").singleResult();

        System.out.println("设置之前，当前执行人是："+task.getAssignee());

        taskService.setAssignee(task.getId(),"大长今");

        System.out.println("设置之后，当前执行人是："+task.getAssignee());
    }

    @Test
    public void testGroup(){

        Group group = identityService.newGroup("PMO");
        group .setName("PMO");
        group .setType("PMO");

        identityService.saveGroup(group);
    }

    @Test
    public void testUser(){

        User user = identityService.newUser("5");
        user.setFirstName("赵六");
        user.setLastName("赵六");
        user.setEmail("赵六@qq.com");

        identityService.saveUser(user);

    }

    @Test
    public void testUserAndGroupMembership(){

        identityService.createMembership("5","PMO");

    }

    public void getCurrentNextUserTaskAssign(){
        //流程定义Id
        String processDefId="project_confirm:3:10004";
        //当前流程节点Id,在任务表里面对应TASK_DEF_KEY_字段，可以根据taskId获取该字段数据
        String flowElemetId = "upload_prototype";
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
        Process process = bpmnModel.getProcesses().get(0);
        //获取所有普通任务节点
        List<UserTask> UserTaskList = process.findFlowElementsOfType(UserTask.class);
        for(UserTask userTask:UserTaskList){
            //获取当前任务节点Id
            String id  = userTask.getId();
            if(id.equals(flowElemetId)){
                //获取当前任务节点的所有出线信息
                List<SequenceFlow> outgoingFlows = userTask.getOutgoingFlows();
                for(SequenceFlow sequenceFlow:outgoingFlows){
                    //获取出线连接的目标节点
                    FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
                    //获取到了下一个节点的Id
                    String nextId = targetFlowElement.getId();

                    List<UserTask> UserTaskLists = process.findFlowElementsOfType(UserTask.class);
                    //再次遍历所有普通任务节点
                    for(UserTask userTasks:UserTaskLists) {
                        //获取任务节点Id
                        String flowId = userTasks.getId();
                        //如果遍历的某个任务节点Id等于下一个节点的Id
                        if (flowId.equals(nextId)) {
                            //获取下一个任务节点的执行人
                            String assignee = userTasks.getAssignee();
                            System.out.println("下一个节点的执行人:"+assignee);
                        }
                    }
                }
            }
        }
    }

}
