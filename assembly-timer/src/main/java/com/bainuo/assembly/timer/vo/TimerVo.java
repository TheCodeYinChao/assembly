package com.bainuo.assembly.timer.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * User: zyc
 * Date: 2018-05-14
 * Time: 13:51
 * Version ：1.0
 * Description:
 */
@Data
public class TimerVo implements Serializable{
    private Integer id;//定时器管理主键',
    private Integer timerFlag;//定时器标识 1 优惠券',
    private String  timerName;//定时器名称',
    private Boolean  switchBtn;//定时器开关 false关闭 true开启',
    private Integer  isvalid;//状态 0、无效 1、有效
}
