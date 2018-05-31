package cn.bainuo.assembly.data.service.coupon;

import cn.bainuo.plugin.ParameterMap;

/**
 * User: zyc
 * Date: 2018-05-16
 * Time: 16:05
 * Version ：1.0
 * Description:
 */
public interface CouponGroupService {
    int deletes(ParameterMap param);

    Object queryData(ParameterMap parameterMap);

    Object saveOrUpdate(ParameterMap parameterMap);

    Object bindCoupons(ParameterMap parameterMap);

    Object getGroupById(ParameterMap parameterMap);

    Object validGroupName(ParameterMap parameterMap);

    Object getCouponListByGroupId(ParameterMap parameterMap);
    Object deleteCouponFromGroup(ParameterMap parameterMap);

    Object selectUnBindCoupon(ParameterMap parameterMap);
}
