package com.raydata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * business_project
 * @author 
 */
@Data
@TableName("business_project")
public class BusinessProject implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

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
     * 项目类型ID
     */
    private Integer projectType;

    /**
     * 项目介绍
     */
    private String intro;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 客户联系方式
     */
    private String clientPhone;

    /**
     * 立项日期
     */
    private Long approvalTime;

    /**
     * 开工日期
     */
    private Long startTime;

    /**
     * 预交付日期
     */
    private Long expectTime;

    /**
     * 项目阶段，初版：0
     */
    private Integer projectPhase;

    /**
     * 开发经理ID
     */
    private String developManager;

    /**
     * 项目状态（10010立项,10011审批通过）
     */
    private Integer projectStatus;

    /**
     * 数据状态（10001未删除,10000已删除）
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

    private static final long serialVersionUID = 1L;

}