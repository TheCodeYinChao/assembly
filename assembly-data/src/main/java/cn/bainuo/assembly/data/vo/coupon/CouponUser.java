package cn.bainuo.assembly.data.vo.coupon;

import lombok.Data;

import java.io.Serializable;

/**
 * User: zyc
 * Date: 2018-05-14
 * Time: 19:47
 * Version ：1.0
 * Description:
 */
@Data
public class CouponUser implements Serializable{
    private String id;
    private String userId;
    private Double money;
    private Integer deadline;
    private Integer createtime;
    private Double needMoney;
    private Integer classType;
    private String couponId;
    private String connectedId;
    private Integer startline;
    private Integer source;
    private Integer giveFlag;
    private Integer isUsed;
    private Integer isvalid;
    private Integer userType;
    private String days;
    private Integer status;
    private Integer daystype;
    private String title;
    private String instruction;

}
