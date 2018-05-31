package cn.bainuo.coupon.center.controller;

import cn.bainuo.coupon.center.client.CouponsFeignClient;
import cn.bainuo.coupon.center.service.CouponService;
import cn.bainuo.coupon.center.vo.CouponVo;
import cn.bainuo.plugin.ParameterMap;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-08
 * Time: 16:36
 * Version ：1.0
 * Description:
 */
@RestController
@Slf4j
public class CouponController {
    @Autowired
    private CouponsFeignClient couponsFeignClient;
    @Autowired
    private CouponService couponService;

    @GetMapping("coupon/{id}")
    public Object getCoupon(@PathVariable Integer id){
        return couponsFeignClient.getCoupon(id);
    }

    @RequestMapping("/queryData")
    public Object queryData(){
        return couponService.get();
    }
}
