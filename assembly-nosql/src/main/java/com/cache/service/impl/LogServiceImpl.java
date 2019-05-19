package com.cache.service.impl;

import com.cache.interceptor.MyThreadPool;
import com.cache.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LogServiceImpl implements LogService {

    @Override
    public void seachLog() {
        MyThreadPool m = new MyThreadPool(1, 2,10,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
        log.info("这里是追踪log");
        new Thread(new Runnable(){
            @Override
            public void run() {
                log.info("子线程1");
            }
        }).start();

        m.execute(new Runnable() {
            @Override
            public void run() {
                log.info("子线程2");
            }
        });
    }
}
