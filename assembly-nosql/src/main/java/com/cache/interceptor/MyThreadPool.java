package com.cache.interceptor;

import org.slf4j.MDC;

import javax.annotation.security.RunAs;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool extends ThreadPoolExecutor {

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        myrun myrun = new myrun(command, context);
        super.execute(myrun);
    }

   /* @Override
    public void execute(Runnable command) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        super.execute(()->run(command,context));
    }*/
    private void run(Runnable command, Map<String, String> context){
        MDC.setContextMap(context);
        try {
            command.run();
        } finally {
            MDC.clear();
        }
    }

    class myrun implements  Runnable{
        private Map<String, String> context;
        private Runnable cammand;
        public myrun(Runnable cammand,Map<String, String> context) {
            this.cammand=cammand;
            this.context=context;
        }

        @Override
        public void run() {
            MDC.setContextMap(context);
            try {
                cammand.run();
            } finally {
                MDC.clear();
            }
        }
    }
}
