package com.raydata.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("role")
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    private String roleName;

    private String roleRemark;

    private Byte roleStatus;

    private Integer systemFlag;

    private Integer roleCreater;

    private Date createTime;

    private Date updateTime;



    @TableField(select=false)
    private List<PerPermission> pers;

  }