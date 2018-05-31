package cn.bainuo.assembly.data.vo.coupon;

import lombok.Data;

import java.io.Serializable;

/**
 * User: zyc
 * Date: 2018-03-21
 * Time: 17:30
 * Version ：1.0
 * Description:
 */
@Data
public class UserCoupon implements Serializable {

    private String couponsId;
    private String couponUsersId;
    private String  days;//截止使用天数.DaysType 为0时,Days保存天数,DaysType为1时,Days保存截止日期
    private Integer classType;//优惠券类型1:全平台、2:分类,3:供应商,4:单品
    private String connectedId;//优惠券类型为1时可以为空,2时是分类id,3时是供应商id,4时是商品spuid
    private Integer startline;//使用开始时间
    private Integer isInitiative;//是否为主动发放优惠券   1:主动发放  0:用户自己领取
    private String title;//代金劵标题
    private Double money;//代金卷金额
    private Double needMoney;//满多少可使用,不限消费金额
    private Integer status;//优惠券状态  1  未发布  2已发布  3 已失效 4 已终止
    private Integer type;//1 满减券 2 满赠券  3  代金券 4 通用券 5 包邮券 6 打折券
    private Integer daysType;//截止时间类型 0:截止天数,1:截止日期
    private Integer userType;//1  所有用户 2 新用户  3 vip1 4 vip2 5 vip3
    private String instruction;//说明
    private Integer createtime;
    private Integer limitNum;//优惠券数量限制 0:不限

    private String userId;//用户user_id
    private Integer deadline;//代金卷截止日期
    private Integer isvalid;//是否有效
    private Integer isUsed;//是否已经使用过 0:未使用 1:使用过 2:冻结.当优惠券被使用，冻结之后,设置isvalid为false
    private String couponId;//领取的代金券ID
}
