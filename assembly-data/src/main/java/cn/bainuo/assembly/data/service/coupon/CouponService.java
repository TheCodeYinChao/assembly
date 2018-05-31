package cn.bainuo.assembly.data.service.coupon;

import cn.bainuo.assembly.data.vo.coupon.CouponUser;
import cn.bainuo.assembly.data.vo.coupon.CouponLog;
import cn.bainuo.assembly.data.vo.coupon.CouponVo;
import cn.bainuo.assembly.data.vo.coupon.Coupons;
import cn.bainuo.plugin.ParameterMap;

import java.util.List;
import java.util.Map;

/**
 * User: zyc
 * Date: 2018-05-08
 * Time: 16:08
 * Version ：1.0
 * Description:
 */
public interface CouponService {
    Coupons selectCoupon(String id);

    List<CouponVo> selectCouponByUserId(String userId);

    List<Coupons> selectCoupons(ParameterMap jsonParseMap);

    Object updateCouponStatus(ParameterMap parameterMap);

    Object deleteCoupon(ParameterMap parameterMap);

    Object saveOrUpdate(ParameterMap parameterMap);

    Object sendStamps(ParameterMap parameterMap);

    Object sendUserStamps(String userId, String couponId,Integer source);

    Object getUserCoupons(ParameterMap parameterMap);

    Object addCouponUser(ParameterMap parameterMap);

    Object editCouponUser(CouponUser couponUser);

    Object selectIsUsing(String data, String userId,Integer platFalg);

    Object getUserCouponList(String userId, Integer platFalg,Integer userType);

    Object saveBatchUserCoupon(String userId, String couponIds,Integer source);
}
