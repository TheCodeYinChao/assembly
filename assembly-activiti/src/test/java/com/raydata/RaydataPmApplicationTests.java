package com.raydata;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;

public class RaydataPmApplicationTests {

    @Test
    public void contextLoads() {
        ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_CREATE).buildProcessEngine();
    }

}
