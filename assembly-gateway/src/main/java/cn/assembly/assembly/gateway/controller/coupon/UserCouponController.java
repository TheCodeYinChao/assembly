package cn.assembly.assembly.gateway.controller.coupon;

import cn.assembly.assembly.gateway.service.coupon.CouponService;
import cn.assembly.assembly.gateway.vo.coupon.Coupons;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

import java.util.HashMap;

/**
 * User: zyc
 * Date: 2018-05-23
 * Time: 15:51
 * Version ：1.0
 * Description:
 */
@RestController
@Slf4j
public class UserCouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/userCoupon/{id}")
    public DeferredResult<HashMap<String,Coupons>> userCoupon(@PathVariable Long id){
        Observable<HashMap<String, Coupons>> result = this.aggregateObservable(id);
        return this.toDeferredResult(result);
    }

    private DeferredResult<HashMap<String,Coupons>> toDeferredResult(Observable<HashMap<String, Coupons>> details) {
        DeferredResult<HashMap<String,Coupons>> result = new DeferredResult<>();
        details.subscribe(new Observer<HashMap<String, Coupons>>() {
            @Override
            public void onCompleted() {
                log.info("完成...");
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("发生错误。。。 ",throwable.getMessage());
            }

            @Override
            public void onNext(HashMap<String, Coupons> stringUserHashMap) {
                result.setResult(stringUserHashMap);
            }
        });
        return result;
    }

    public Observable<HashMap<String,Coupons>> aggregateObservable(Long id){
        return Observable.zip(
                this.couponService.selectById(id),
                this.couponService.selectByIds(id),
                (coupons, couponsUser)->{
                    HashMap<String,Coupons> map = Maps.newHashMap();
                    map.put("coupons",coupons);
                    map.put("couponsUser",couponsUser);
                    return map;
                }
        );
    }
}
