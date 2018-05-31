package com.bainuo.assembly.timer.timerservice.impl;

import com.bainuo.assembly.timer.dao.common.CommonMapper;
import com.bainuo.assembly.timer.dao.coupon.CouponMapper;
import com.bainuo.assembly.timer.timerservice.CouponTimerService;
import com.bainuo.assembly.timer.vo.Coupons;
import com.bainuo.assembly.timer.vo.TimerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-14
 * Time: 11:56
 * Version ：1.0
 * Description:
 */
@Service
@Transactional
@Slf4j
public class CouponTimerServiceImpl implements CouponTimerService {
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private CouponMapper couponMapper;

    @Override
    public void handleTimer(){
        TimerVo timerVo = commonMapper.selectById(1);

        if(timerVo != null && timerVo.getSwitchBtn()){
            log.info("CommonServiceImpl-handleTimer {}","CouponTimer Start .....");
            //查询status不等于三的
            List<Coupons> coupons =  couponMapper.selectByStatus();
            //找到需要过期的
            List<String> couponIds = new LinkedList<String>();
            long timeMillis = System.currentTimeMillis();
            for(Coupons coupon:coupons){
                if(coupon.getDaystype()==1){//截止日期
                    if(timeMillis > Long.valueOf(coupon.getDays())*1000){
                        couponIds.add(coupon.getId());
                    }
                }else{//天
                    Integer startline = coupon.getStartline();
                    if(startline != null){
                        int day = Integer.valueOf(coupon.getDays());
                        int validity = day*24*60*60*1000;
                        int end = startline + validity;
                        if(timeMillis > Long.parseLong(end+"")){
                            couponIds.add(coupon.getId());
                        }
                    }
                }
            }
            log.info("CommonServiceImpl-handleTimer Size Update {}",couponIds.size());
            //更新
            if(!couponIds.isEmpty()){
                couponMapper.updateStatus(couponIds);
            }
            log.info("CommonServiceImpl-handleTimer {}","CouponTimer End .....");
        }else{
            log.info("CommonServiceImpl-handleTimer {}","CouponTimer Is Not Open Or Not Setting ！！！");
        }
    }

}
