package com.cache.service.impl;

import com.cache.lock.service.DistributedLockByZookeeper;
import com.cache.service.LockZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LockZookeeperServiceImpl implements LockZookeeperService {
    @Autowired
    private DistributedLockByZookeeper distributedLockByZookeeper;


    private final static String PATH = "test";

    public String getLock1() {
        Boolean flag;
        distributedLockByZookeeper.acquireDistributedLock(PATH);
        try {
            Thread.sleep(1000);
            String lock2 = getLock2();/*非重入，会阻塞*/
            log.info("验证是否能够重入:{}",lock2);
        } catch (InterruptedException e) {
            e.printStackTrace();
            flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
        }
        flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
        return flag.toString();
    }

    public String getLock2() {
        Boolean flag;
        distributedLockByZookeeper.acquireDistributedLock(PATH);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
        }
        flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
        return flag.toString();
    }

}
