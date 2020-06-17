package com.raydata;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.slf4j.LoggerFactory;


public class MyUnitTest1 {
    private static org.slf4j.Logger Log = LoggerFactory.getLogger(MyUnitTest1.class);
	
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Deployment(resources = {"org/activiti/test/process.bpmn20.bpmn"})
	public void test() {
		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess_1");

    }
}
