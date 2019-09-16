package com.raydata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("pm_dictionaries")
public class PmDictionaries implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Byte type; //'0 区域 1行业 2项目类型

    private String name;

    private Integer sort;

    private Integer status;

    private Date createtime;

    private Date updatetime;
}