package cn.bainuo.assembly.data.controller.opt;

import cn.bainuo.assembly.data.service.coupon.CouponPackService;
import cn.bainuo.assembly.data.service.coupon.CouponService;
import cn.bainuo.controller.BaseController;
import cn.bainuo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: zyc
 * Date: 2018-05-15
 * Time: 11:07
 * Version ：1.0
 * Description:
 */
@RestController
@RequestMapping("/couponPack")
public class CouponPackController extends BaseController {
    @Autowired
    private CouponPackService couponPackService;
    /**
     * 绑定大礼包,优惠券组
     * @param param
     * @return
     */
    @PostMapping("/bindpacks")
    public Object bindpacks(@RequestBody(required = false)String param){
        return ResultVo.ok(couponPackService.bandpacks(this.jsonParseMap(param)));
    }
}
