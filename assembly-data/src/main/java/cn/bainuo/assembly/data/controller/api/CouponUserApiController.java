package cn.bainuo.assembly.data.controller.api;

import cn.bainuo.assembly.data.service.coupon.CouponService;
import cn.bainuo.assembly.data.service.coupon.CouponUserService;
import cn.bainuo.plugin.ParameterMap;
import cn.bainuo.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: zyc
 * Date: 2018-05-25
 * Time: 17:27
 * Version ：1.0
 * Description:
 */
@RestController
@RequestMapping("/api/couponUser")
@Api(value="用户- 优惠券",tags={"Coupon User- Api"})
public class CouponUserApiController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponUserService couponUserService;

    @PostMapping("/saveBatchUserCoupon")
    @ApiOperation(value = "用户批量领券")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "userId", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "couponIds", value = "券id，多个‘，’隔开", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "source", value = "1礼包 2 蔬菜 3小菜", required = true, dataType = "Integer")
    })
    public Object saveBatchUserCoupon(@RequestParam(required = false) String userId,
                                      @RequestParam(required = false) String couponIds,
                                      @RequestParam(defaultValue = "1") Integer source){
        return ResultVo.ok(couponService.saveBatchUserCoupon(userId,couponIds,source));
    }

    /**
     * 发放优惠券
     * @param userId
     * @param couponId
     * @return
     */
    @PostMapping("/sendStamps")
    @ApiOperation(value = "发放优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "couponId", value = "优惠券Id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "source", value = "来源 1、大礼包 2、蔬菜盒子 3、小菜 ", required = true, dataType = "Integer")
    })
    public Object sendStamps(@RequestParam(required = false)String userId,
                             @RequestParam(required = false) String couponId,
                             @RequestParam(required = false) Integer source){
        return ResultVo.ok(couponService.sendUserStamps(userId,couponId,source));
    }


    /**
     *用户优惠券
     * @param userId
     * @param platFalg
     * @return
     */
    @PostMapping("/getUserCoupons")
    @ApiOperation(value = "获取用户优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "platFalg", value = "平台标示", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userType", value = "1新用户优惠,2个人优惠,3邀请好友优惠", required = false, dataType = "Integer")
    })
    public Object getUserCoupons(@RequestParam(required = false) String userId,
                                 @RequestParam(required = false) Integer platFalg,
                                 @RequestParam(required = false)Integer userType){
        return ResultVo.ok(couponService.getUserCouponList(userId,platFalg,userType));
    }

    /**
     * 变更优惠券状态
     * @return
     */
    @PostMapping("/updateState")
    @ApiOperation(value = "用户-优惠券 status update")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "ids more , split", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "status 0:未使用 1:使用过 2:冻结", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "userId", required = true, dataType = "String")})
    public Object updateState(@RequestParam(required = false) String ids ,
                              @RequestParam(required = false)Integer status,
                              @RequestParam(required = false)String userId){
        ParameterMap pm = new ParameterMap();
        pm.put("id",ids);
        pm.put("status",status);
        pm.put("userId",userId);
        return ResultVo.ok(couponService.updateCouponStatus(pm));
    }

    /**
     *校验优惠券数量是否达到上限
     * @param userId
     * @param couponIds
     * @return
     */
    @PostMapping("/validIsBuildLimit")
    @ApiOperation(value = "用户-优惠券 count 校验")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "userId", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "couponIds", value = "couponIds more , split", required = true, dataType = "String")})
    public Object validIsBuildLimit(String userId,String couponIds){
        return ResultVo.ok(couponUserService.validCouponUserCount(userId,couponIds));
    }
}
