package cn.bainuo.coupon.center.client;

import cn.bainuo.coupon.center.vo.CouponVo;
import cn.bainuo.plugin.ParameterMap;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-08
 * Time: 17:06
 * Version ：1.0
 * Description:
 */
@FeignClient("assembly-data")
public interface CouponsFeignClient {

    @RequestMapping(value = "/coupon/getCoupon/{id}",method = RequestMethod.GET)
    public CouponVo getCoupon(@PathVariable("id") Integer id);

    @RequestMapping(value = "/coupon/couponList",method = RequestMethod.POST)
    public String couponList(@RequestBody String param);
}
