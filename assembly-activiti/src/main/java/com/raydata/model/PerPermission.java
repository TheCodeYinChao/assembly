package com.raydata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("per_permission")
public class PerPermission implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer perId;

    private Integer perPid;

    private String perName;

    private String perEnglishname;

    private String perAction;

    private String perUrl;

    private String perIcon;

    private Byte perType;

    private String perDescribe;

    private Integer perSort;

    private Integer systemFlag;

    private Byte perStatus;

    private Integer creator;

    private Date createTime;

    private Date updateTime;

    @TableField(select = false)
    private List<PerPermission> menus;
    @TableField(select = false)
    private List<PerPermission> buttons;
}