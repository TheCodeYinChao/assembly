package com.bainuo.assembly.timer.vo;

import lombok.Data;

@Data
public class Coupons {
    private String id;

    private String title;

    private Byte type;

    private Byte status;

    private Double money;

    private Double needmoney;

    private Integer daystype;

    private String days;

    private Integer startline;

    private Integer classType;

    private String connectedId;

    private String connectedContent;

    private Byte userType;

    private Boolean isvalid;

    private Integer createtime;

    private Integer cangetnum;

    private Integer isInitiative;

    private String instruction;
    private Boolean binded;

    private String reationId;
}