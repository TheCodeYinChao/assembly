package cn.bainuo.assembly.data.service.coupon;

/**
 * User: zyc
 * Date: 2018-05-25
 * Time: 17:51
 * Version ：1.0
 * Description:
 */
public interface CouponUserService {
    /**
     * 检验用户是否达到券数量
     * @param userId
     * @param couponIds
     * @return
     */
    Object validCouponUserCount(String userId,String couponIds);
}
