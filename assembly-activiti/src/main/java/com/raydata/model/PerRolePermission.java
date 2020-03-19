package com.raydata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("per_role_permission")
public class PerRolePermission implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer roleId;

    private Integer perId;
}