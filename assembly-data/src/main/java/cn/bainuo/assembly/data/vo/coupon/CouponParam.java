package cn.bainuo.assembly.data.vo.coupon;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User: zyc
 * Date: 2018-03-21
 * Time: 17:06
 * Version ：1.0
 * Description:
 */
@Data
public class CouponParam implements Serializable{

    private String spuId;
    private String skuId;
    private BigDecimal perPirce;
    private String firstCategoryId;
    private String twoCategoryId;
    private Integer count;

}
