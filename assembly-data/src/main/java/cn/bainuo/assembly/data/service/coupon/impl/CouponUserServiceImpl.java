package cn.bainuo.assembly.data.service.coupon.impl;

import cn.bainuo.assembly.data.dao.coupon.CouponMapper;
import cn.bainuo.assembly.data.dao.coupon.CouponUserMapper;
import cn.bainuo.assembly.data.service.coupon.CouponService;
import cn.bainuo.assembly.data.service.coupon.CouponUserService;
import cn.bainuo.assembly.data.vo.coupon.Coupons;
import cn.bainuo.exception.ServiceException;
import cn.bainuo.exception.exceptionenum.GlobalErrorCode;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: zyc
 * Date: 2018-05-25
 * Time: 17:52
 * Version ：1.0
 * Description:
 */
@Service
public class CouponUserServiceImpl implements CouponUserService {
    @Autowired
    private CouponUserMapper couponUserMapper;
    @Autowired
    private CouponMapper couponMapper;


    @Override
    public Object validCouponUserCount(String userId, String couponIds) {

        if(StringUtils.isEmpty(userId)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"userId Not Null");
        }
        if(StringUtils.isEmpty(couponIds)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"couponIds Not Null");
        }
        List<String> ids = Arrays.asList(couponIds.split(","));
        List<Coupons> coupons = couponMapper.selectCouponsByIds(ids);

        List<Map<String,Object>> rs = couponUserMapper.selectUserCouponCount(userId,ids);
        List<String> coupon = getUpCoupon(coupons, rs);
        Map<String,Object> map = new HashMap();
        if(coupon != null && coupon.size() > 1){
            map.put("valid",false);
            map.put("msg","列表中有到达最大领取次数");
            map.put("couponIds", JSON.toJSON(coupon));
        }else{
            map.put("valid",true);
            map.put("msg","没有到达最大领取次数的券");
            map.put("couponIds", null);
        }
        return map;
    }

    private  List<String> getUpCoupon(List<Coupons> arg0,List<Map<String,Object>> arg1){
        List<String> rs = new ArrayList<String>();
        for(Coupons coupon:arg0){
            Integer num = coupon.getLimitNum();
            String couponId = coupon.getId();
            if(num != 0){
                for(Map<String,Object>  r: arg1){
                    String id = (String)r.get("couponId");
                    if(couponId.equals(id)){
                        Integer count= (Integer) r.get("couponCount");
                        if(count >= num){
                            rs.add(id);
                            break;
                        }
                    }
                }
             }
        }
        return rs;
    }
}
