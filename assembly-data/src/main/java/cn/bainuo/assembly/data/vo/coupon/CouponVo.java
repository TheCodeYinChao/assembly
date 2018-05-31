package cn.bainuo.assembly.data.vo.coupon;

import lombok.Data;

import java.io.Serializable;

/**
 * User: zyc
 * Date: 2018-05-08
 * Time: 16:11
 * Version ：1.0
 * Description:
 */
@Data
public class CouponVo implements Serializable {
    private Integer id;
    private String title;
    private Double needMoney;
}
