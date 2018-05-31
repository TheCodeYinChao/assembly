package cn.bainuo.coupon.center.service.impl;

import cn.bainuo.coupon.center.client.CouponsFeignClient;
import cn.bainuo.coupon.center.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: zyc
 * Date: 2018-05-09
 * Time: 17:19
 * Version ：1.0
 * Description:
 */
@Service
@Slf4j
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponsFeignClient couponsFeignClient;


    public Object get(){
        String param="{rows:10,page:1}";
        String s = couponsFeignClient.couponList(param);
        log.info("[Coupon-queryData] ", s);
        return s;
    }

}
