package cn.bainuo.assembly.data.dao.coupon;

import cn.bainuo.assembly.data.vo.coupon.CouponUser;
import cn.bainuo.assembly.data.vo.coupon.CouponVo;
import cn.bainuo.assembly.data.vo.coupon.Coupons;
import cn.bainuo.assembly.data.vo.coupon.UserCoupon;
import cn.bainuo.plugin.ParameterMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-08
 * Time: 16:06
 * Version ：1.0
 * Description:
 */
@Repository
public interface CouponMapper {
    Coupons selectCouponById(@Param("id") String id);

    List<CouponVo> selectCouponByUserId(@Param("userId")String userId);

    List<Coupons> selectCouponPage(ParameterMap jsonParseMap);

    int updataStatus(@Param("ids") List<String> ids, @Param("status") String status,@Param("userId")String userId);

    int delete(@Param("couponIds")List<String> couponIds);

    int insertCoupon(ParameterMap parameterMap);

    int updateCoupon(ParameterMap parameterMap);

    int insertUserStamp(ParameterMap parameterMap);

    int insertUserCoupon(CouponUser couponUser);

    CouponUser selectCouponByCouponUserId(CouponUser couponUser);

    /***
     * 用户解绑优惠券，更改用户优惠券状态为无效
     * @Param("ids") List<String> ids, @Param("status") String status
     * return 变更结果
     * */
    int updateCouponUser(@Param("id")  String id);

    int deleteUserCoupon(@Param("ids")List<String> ids,@Param("userId")String userId);

    List<UserCoupon> getIsUsIngByUserId(@Param("userId") String userId,@Param("platFalg")Integer platFalg,@Param("userType")Integer userType);

    int selectUserCouponCount(@Param("userId") String userId,@Param("couponId")String couponId);

    List<UserCoupon> selectUserCouponPage(ParameterMap parameterMap);

    int saveBatchUserCoupons(@Param("userCoupons") List<CouponUser> userCoupons);

    List<Coupons> selectCouponsByIds(@Param("ids") List<String> ids);
}
