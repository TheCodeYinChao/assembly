package com.netty.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Admin on 2019/4/15.
 */
public class FutureDemo {
    public static void main(String[] args)throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(1);

        Future<Long> future = es.submit(new FutureDemo().new Task());

        System.out.println(future.get());
    }


    public class Task implements Callable<Long>{
        @Override
        public Long call() throws Exception {
            return 123l;
        }
    }
}
