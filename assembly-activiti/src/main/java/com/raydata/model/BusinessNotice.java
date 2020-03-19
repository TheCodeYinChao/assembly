package com.raydata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * business_notice
 * @author 
 */
@Data
@TableName("business_notice")
public class BusinessNotice implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 表单ID
     */
    private String formId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 标题
     */
    private String title;

    /**
     * 知会人ID
     */
    private String noticePerson;

    /**
     * 是否已读（0未读，1已读）
     */
    private boolean ifRead;

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