package cn.bainuo.assembly.data.controller.opt;

import cn.bainuo.assembly.data.service.coupon.CouponLogService;
import cn.bainuo.controller.BaseController;
import cn.bainuo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: zyc
 * Date: 2018-05-18
 * Time: 14:25
 * Version ：1.0
 * Description:
 */
@RestController
@RequestMapping("couponLog")
public class CouponLogController extends BaseController{
    @Autowired
    private CouponLogService couponLogService;

    @PostMapping("/queryData")
    public Object queryData(@RequestBody(required = false) String param){
        return ResultVo.ok(couponLogService.queryData(this.jsonParseMap(param)));
    }

}
