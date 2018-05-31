package cn.bainuo.assembly.data.controller.api;

import cn.bainuo.assembly.data.service.coupon.CouponService;
import cn.bainuo.controller.BaseController;
import cn.bainuo.plugin.ParameterMap;
import cn.bainuo.vo.ResultVo;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * User: zyc
 * Date: 2018-05-14
 * Time: 18:33
 * Version ：1.0
 * Description:
 */
@RestController
@RequestMapping("/api/coupon")
@Api(value="优惠券",tags={"Coupon - Api"})
public class CouponApiController  extends BaseController {
   @Autowired
   private CouponService couponService;

    /**
     * 查询优惠券详情
     * @return
     */
    @PostMapping("/couponDetail")
    @ApiOperation(value = "优惠券详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "优惠券Id", required = true, dataType = "String")})
    public Object couponDetail(@RequestParam(required = false) String id ){
        return ResultVo.ok(couponService.selectCoupon(id));
    }

    /**
     * 本单可用
     * @return
     */
    @PostMapping("/useIng")
    @ApiOperation(value = "the order can useing")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "userId", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "data", value = "下单 json data", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "platFalg", value = "平台 platFalg", required = true, dataType = "Integer")})
    public Object useIng(@RequestParam(required = false) String userId,
                         @RequestParam(required = false) String data,
                         @RequestParam(required = false) Integer platFalg){
        return ResultVo.ok(couponService.selectIsUsing(data,userId,platFalg));
    }

    /**
     * 根据平台id 获取 coupons
     * @param platFalg
     * @return
     */
    @PostMapping("/couponsByPlatFalg")
    @ApiOperation(value = "根据平台id 获取 coupons")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "platFalg", value = "platFalg", required = true, dataType = "Integer")
          })
    public  Object couponsByPlatFalg(@RequestParam(required = false) Integer platFalg){
        ParameterMap pm = new ParameterMap();
        pm.put("platFalg",platFalg);
        return ResultVo.ok(couponService.selectCoupons(pm));
    }

}
