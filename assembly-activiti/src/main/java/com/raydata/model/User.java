package com.raydata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName("user")
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String userLogin;

    private String password;

    private String userName;

    private String dictionName;

    private String companyEmail;

    private String telphone;

    private String userNo;

    private String area;

    private String industry;

    private Byte sex;

    private Date birthday;

    private String idcard;

    private String nation;

    private String politics;

    private String birthplaceCode;

    private String birthplace;

    private Byte ismerried;

    private String accountType;

    private String nativePlace;

    private Date workDate;

    private Date joinDate;

    private Date leaveDate;

    private Byte isrejoin;

    private Byte userType;

    private Byte probation;

    private String emergencyContact;

    private String emergencyContactTelphone;

    private Byte hascontract;

    private Byte contractState;

    private Integer seniority;

    private Byte education;

    private String staffLevel;

    private String personalEmail;

    private String residenceAddressCode;

    private String residenceAddress;

    private String homeTelephone;

    private String workTelephone;

    private String remark;

    private Integer sort;

    private Integer status;

    private Integer creator;

    private Date createTime;

    private Date updateTime;


    @TableField(select = false)
    private List<Role> roles;
    @TableField(select = false)
    private List<PmDictionaries> areas;
    @TableField(select = false)
    private List<PmDictionaries> industrys;
}