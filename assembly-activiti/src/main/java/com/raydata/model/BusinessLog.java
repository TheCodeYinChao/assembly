package com.raydata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * business_log
 * @author 
 */
@Data
@TableName("business_log")
public class BusinessLog implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 日志id
     */
    private String logId;

    /**
     * 表单id
     */
    private String fromId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 操作名称
     */
    private String operationId;

    private String operationName;

    /**
     * 操作人id
     */
    private String operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人职位，同操作人角色名称
     */
    private String operatorPositionName;

    /**
     * 操作人职位id，同操作人角色id
     */
    private Integer operatorPositionId;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 日志内容
     */
    private String logContent;

    private static final long serialVersionUID = 1L;

}