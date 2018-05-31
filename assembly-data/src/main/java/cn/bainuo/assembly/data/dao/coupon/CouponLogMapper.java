package cn.bainuo.assembly.data.dao.coupon;

import cn.bainuo.assembly.data.vo.coupon.CouponLog;
import cn.bainuo.plugin.ParameterMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-15
 * Time: 19:13
 * Version ：1.0
 * Description:
 */
@Repository
public interface CouponLogMapper {
    int insertCouponLog(@Param("logs") List<CouponLog> logs);

    List<CouponLog> queryDataPage(ParameterMap parameterMap);
}
