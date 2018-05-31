package cn.bainuo.assembly.data.controller.opt;

import cn.bainuo.assembly.data.service.coupon.CouponService;
import cn.bainuo.assembly.data.vo.coupon.CouponUser;
import cn.bainuo.assembly.data.vo.coupon.CouponVo;
import cn.bainuo.assembly.data.vo.coupon.Coupons;
import cn.bainuo.controller.BaseController;
import cn.bainuo.plugin.ParameterMap;
import cn.bainuo.vo.ResultVo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.List;
import java.util.Map;

/**
 * User: zyc
 * Date: 2018-05-08
 * Time: 16:10
 * Version ：1.0
 * Description:
 */
@RestController
@RequestMapping("/coupon")
public class CouponController extends BaseController{
    @Autowired
    private CouponService couponService;

    /**
     * 根据优惠券Id查询优惠券
     * @param id
     * @return
     */
    @GetMapping("/getCoupon/{id}")
    public Object getCouponById(@PathVariable String id){
        return ResultVo.ok(couponService.selectCoupon(id));
    }

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/gCByUserId/{userId}")
    public Object couponVos(@PathVariable String userId){
        return ResultVo.ok(couponService.selectCouponByUserId(userId));
    }

    /**
     * 优惠券列表
     *
     * @return
     */
    @PostMapping("/couponList")
    public Object couponList(@RequestBody(required = false) String param){
        ParameterMap parameterMap = this.jsonParseMap(param);
        List<Coupons> ls = couponService.selectCoupons(parameterMap);
        parameterMap.put("data",ls);
        return ResultVo.ok(parameterMap);
    }

    /**
     * 变更人员优惠券状态
     * @param param
     * @return
     */
    @PostMapping("/update")
    public Object updateStatus(@RequestBody(required = false)String param){
        Object data = couponService.updateCouponStatus(this.jsonParseMap(param));
        return ResultVo.ok(data);
    }

    /**
     * 批量删除
     * @param param
     * @return
     */
    @PostMapping("/batchDelete")
    public Object deleteCoupon(@RequestBody(required = false)String param){
        return ResultVo.ok(couponService.deleteCoupon(this.jsonParseMap(param)));
    }

    /**
     * 添加和更新
     * @param param
     * @return
     */
    @PostMapping("/addOrUpdata")
    public Object add(@RequestBody(required = false)String param){
        return ResultVo.ok(couponService.saveOrUpdate(this.jsonParseMap(param)));
    }
    /**
     * 添加用户优惠券
     * @param param
     * @return
     */
    @PostMapping("/addCouponUser")
    public Object addCouponUser(@RequestBody(required = false)String param){
        Object data = couponService.addCouponUser(this.jsonParseMap(param));
        return ResultVo.ok(data);
    }

    /**
     * 解绑用户优惠券
     * @param couponUser
     * @return
     */
    @PostMapping("/editCouponUser")
    public Object editCouponUser(@RequestBody(required = false)CouponUser couponUser){
        Object data = couponService.editCouponUser(couponUser);
        return ResultVo.ok(data);
    }

}
