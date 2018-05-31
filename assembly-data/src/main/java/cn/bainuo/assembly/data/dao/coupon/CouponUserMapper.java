package cn.bainuo.assembly.data.dao.coupon;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * User: zyc
 * Date: 2018-05-25
 * Time: 18:19
 * Version ：1.0
 * Description:
 */
@Repository
public interface CouponUserMapper {

    List<Map<String,Object>> selectUserCouponCount(@Param("userId") String userId,@Param("ids")List<String> ids);
}