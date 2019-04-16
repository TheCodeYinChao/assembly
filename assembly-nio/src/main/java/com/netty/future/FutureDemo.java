package com.netty.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * jdk 内置future实现
 */
@Slf4j
public class FutureDemo {

    public static void main(String[] args) throws Exception{
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Long> future = es.submit(new FutureDemo().new T());
        log.info( "异步结果获取：{}",future.get());
    }



     public class T implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            Thread.sleep(2000);
            return 0l;
        }
    }
}
