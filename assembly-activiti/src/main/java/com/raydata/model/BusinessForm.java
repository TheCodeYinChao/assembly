package com.raydata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * business_form
 * @author 
 */
@Data
@TableName("business_form")
public class BusinessForm implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 表单ID
     */
    private String formId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 操作按钮ID
     */
    private Integer operationId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 行业前景
     */
    private Integer industryFuture;

    /**
     * 项目规模
     */
    private Integer projectScale;

    /**
     * 客户属性
     */
    private Integer customerAttribute;

    /**
     * 制作量
     */
    private Integer productionQuantity;

    /**
     * 开工日期
     */
    private Long startTime;

    /**
     * 预交付日期
     */
    private Long expectTime;

    /**
     * 备注
     */
    private String remark;

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

    private static final long serialVersionUID = 1L;

}