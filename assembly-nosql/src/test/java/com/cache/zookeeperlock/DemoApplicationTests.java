package com.cache.zookeeperlock;

import com.cache.service.LockZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {
    @Autowired private LockZookeeperService service;

    @Test
    public void test1() throws Exception{
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                String lock1 = service.getLock1();
                log.info("lock1 执行结果：{}",lock1);
            }
        });
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                String lock2 = service.getLock1();
                log.info("lock2 执行结果：{}",lock2);
            }
        });
        a.start();
        b.start();
        b.join();
        a.join();
    }
}
