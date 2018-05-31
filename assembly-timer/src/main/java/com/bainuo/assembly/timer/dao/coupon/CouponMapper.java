package com.bainuo.assembly.timer.dao.coupon;

import com.bainuo.assembly.timer.vo.Coupons;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-14
 * Time: 13:30
 * Version ：1.0
 * Description:
 */
@Repository
public interface CouponMapper {
    List<Coupons> selectByStatus();

    int updateStatus(@Param("couponIds") List<String> couponIds);
}
