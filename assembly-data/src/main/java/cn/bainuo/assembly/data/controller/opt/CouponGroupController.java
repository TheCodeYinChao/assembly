package cn.bainuo.assembly.data.controller.opt;

import cn.bainuo.assembly.data.service.coupon.CouponGroupService;
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
 * Date: 2018-05-16
 * Time: 16:01
 * Version ：1.0
 * Description:
 */
@RestController
@RequestMapping("couponGroup")
public class CouponGroupController extends BaseController {

    @Autowired
    private CouponGroupService couponGroupService;

    /**
     * 保存/更新组
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody (required = false) String param){
        return ResultVo.ok(couponGroupService.saveOrUpdate(this.jsonParseMap(param)));
    }

    /**
     * 删除组
     * @param param
     * @return
     */
    @PostMapping("/delete")
    public Object delete(@RequestBody (required = false) String param){
        return ResultVo.ok(couponGroupService.deletes(this.jsonParseMap(param)));
    }

    /**
     * 组列表
     * @param param
     * @return
     */
    @PostMapping("/groupList")
    public Object groupList(@RequestBody (required = false) String param){
        return ResultVo.ok(couponGroupService.queryData(this.jsonParseMap(param)));
    }

    /**
     * 组 绑定 优惠券
     * @param param
     * @return
     */
    @PostMapping("/bindCoupons")
    public Object bindCoupons(@RequestBody(required = false) String param){
        return  ResultVo.ok(couponGroupService.bindCoupons(this.jsonParseMap(param)));
    };

    /**
     * 查询某个优惠券组详情
     * @param param
     * @return
     */
    @PostMapping("/getGroupById")
    public Object getGroupById(@RequestBody(required = false) String param){
        return  ResultVo.ok(couponGroupService.getGroupById(this.jsonParseMap(param)));
    };

    /**
     * 检验组名不重复
     * @param param
     * @return
     */
    @PostMapping("/validGroupName")
    public Object validGroupName(@RequestBody(required = false)String param){
        return ResultVo.ok(couponGroupService.validGroupName(this.jsonParseMap(param)));
    }

    /**
     * 获取组已绑定的优惠券
     * @param param
     * @return
     */
    @PostMapping("/bindGoupons")
    public Object getCouponListByGroupId(@RequestBody(required = false)String param){
        return ResultVo.ok(couponGroupService.getCouponListByGroupId(this.jsonParseMap(param)));
    }

    /**
     * 批量删除组中优惠券
     * @param param
     * @return
     */
    @PostMapping("/deleteCouponFromGroup")
    public Object deleteCouponFromGroup(@RequestBody(required = false)String param){
        return ResultVo.ok(couponGroupService.deleteCouponFromGroup(this.jsonParseMap(param)));
    }

    /**
     * 查询当前组未绑定的券
     * @param param
     * @return
     */
    @PostMapping("/selectUnBindCoupon")
    public Object selectUnBindCoupon(@RequestBody(required = false)String param){
        return ResultVo.ok(couponGroupService.selectUnBindCoupon(this.jsonParseMap(param)));
    }
}
