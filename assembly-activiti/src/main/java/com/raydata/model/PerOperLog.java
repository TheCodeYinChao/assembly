package com.raydata.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("per_oper_log")
public class PerOperLog  implements Serializable {
    @TableId
    private Integer id;

    private Byte type;

    private Integer oper;

    private Date opertime;

    private String pers;

    private String beoper;



    private Date begin;
    private Date end;

}