package cn.assembly.assembly.gateway.service.coupon;

import cn.assembly.assembly.gateway.vo.coupon.Coupons;
import org.springframework.stereotype.Service;
import rx.Observable;

/**
 * User: zyc
 * Date: 2018-05-23
 * Time: 15:54
 * Version ：1.0
 * Description:
 */
@Service
public class CouponService {

    public Observable<Coupons> selectById(Long id) {
        return Observable.create(observer ->{
           Coupons coupons = new Coupons();
            observer.onNext(coupons);
            observer.onCompleted();
        });
    }

    public Observable<Coupons> selectByIds(Long id) {
        return Observable.create(observer ->{
            Coupons coupons = new Coupons();
            observer.onNext(coupons);
            observer.onCompleted();
        });
    }
}
