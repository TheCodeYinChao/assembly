package cn.bainuo.assembly.data.controller.opt;

import cn.bainuo.assembly.data.service.coupon.CouponService;
import cn.bainuo.controller.BaseController;
import cn.bainuo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: zyc
 * Date: 2018-05-24
 * Time: 19:33
 * Version ：1.0
 * Description:
 */
@RestController
@RequestMapping("/userCoupon")
public class CouponUserController extends BaseController {
    @Autowired
    private CouponService couponService;

    /**
     * 用户优惠券
     * @param param
     * @return
     */
    @PostMapping("/getUserCoupons")
    public Object getUserCoupons(@RequestBody(required = false)String param){
        return ResultVo.ok(couponService.getUserCoupons(this.jsonParseMap(param)));
    }

    /**
     * 用户优惠券
     * @param param
     * @return
     */
    @PostMapping("/sendStamps")
    public Object saveUserCoupon(@RequestBody(required = false)String param){
        return ResultVo.ok(couponService.sendStamps(this.jsonParseMap(param)));
    }
}
