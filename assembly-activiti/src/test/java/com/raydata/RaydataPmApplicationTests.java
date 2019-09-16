package com.raydata;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class RaydataPmApplicationTests {

    @Test
    public void contextLoads() {
        ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_CREATE).buildProcessEngine();
    }

}
