package com.raydata.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 审批数据
 */
@Data
public class CheckInfo implements Serializable{

    private static final long serialVersionUID = 1L;
    //任务名称
    private String taskName;
    private String taskKey;
    //处理结果
    private Boolean result;
    //审批备注
    private String remark;
    private String branchName;
    private String departmentName;
    private String roleName;
    private String userName;
    private Date doTime = new Date();
}
