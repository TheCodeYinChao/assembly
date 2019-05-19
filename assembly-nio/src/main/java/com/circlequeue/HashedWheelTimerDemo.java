package com.circlequeue;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class HashedWheelTimerDemo {
    public static void main(String[] args) {

        HashedWheelTimer hwt = new HashedWheelTimer(12, TimeUnit.MICROSECONDS, 500);
        hwt.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("资源会丢失问题！不宜使用");
                System.out.println("延迟任务执行");
                System.out.println("查询修改订单是否支付，未支付则更改订单状态为已失效");
            }
        },30,TimeUnit.SECONDS);

        hwt.start();

    }
}
