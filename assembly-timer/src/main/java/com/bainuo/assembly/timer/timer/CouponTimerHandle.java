package com.bainuo.assembly.timer.timer;

import com.bainuo.assembly.timer.timerservice.CouponTimerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: zyc
 * Date: 2018-05-14
 * Time: 11:55
 * Version ：1.0
 * Description:
 */
@Component
@Slf4j
public class CouponTimerHandle {

    @Autowired
    private CouponTimerService couponTimerService;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void autoInvaild(){
        try {
            couponTimerService.handleTimer();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CouponTimerHandle-autoInvaild Error :{}",e.getMessage());
        }
    }
}
