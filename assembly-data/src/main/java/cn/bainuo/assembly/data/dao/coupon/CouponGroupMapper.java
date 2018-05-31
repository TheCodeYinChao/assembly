package cn.bainuo.assembly.data.dao.coupon;

import cn.bainuo.assembly.data.vo.coupon.Coupons;
import cn.bainuo.assembly.data.vo.coupon.CouponsGroup;
import cn.bainuo.plugin.ParameterMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-16
 * Time: 16:07
 * Version ：1.0
 * Description:
 */
@Repository
public interface CouponGroupMapper {
    int deletes(@Param("ids") List<String> ids);

    List<CouponsGroup> queryDataPage(ParameterMap parameterMap);

    int updateGroup(CouponsGroup CouponsGroup);

    int insertGroup(CouponsGroup CouponsGroup);

    int deleteGroupCoupon(@Param("groupId") String groupId);

    int baindCoupons(@Param("groupId") String groupId,@Param("couponIds") List<String> couponIds,@Param("createtime")Integer createtime);

    CouponsGroup selectById(@Param("id")String id);

    int selectCount(@Param("groupName")String groupName,@Param("groupId") String groupId);

    List<Coupons> selectCouponGroupListPage(ParameterMap parameterMap);

    int deleteCouponFromGroup(@Param("groupId") String groupId,@Param("couponIds") List<String> couponIds);

    List<Coupons> selectUnBindByGroupIdPage(ParameterMap parameterMap);

}
