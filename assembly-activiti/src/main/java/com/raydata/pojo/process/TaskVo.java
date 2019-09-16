package com.raydata.pojo.process;// +----------------------------------------------------------------------

import lombok.Data;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/12
// +----------------------------------------------------------------------
// | Time: 10:33
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@Data
public class TaskVo {

    /**
     * 项目ID，activiti 的bussiness_key
     */
    private String projectId;

    /**
     * 项目编号
     */
    private String projectNumber;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 行业ID
     */
    private Integer industry;

    /**
     * 区域ID
     */
    private Integer area;

    /**
     * 任务ID
     */
    private String taskId;

}
