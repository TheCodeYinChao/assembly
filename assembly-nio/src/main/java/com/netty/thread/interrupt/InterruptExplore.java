package com.netty.thread.interrupt;

public class InterruptExplore {
    public static void main(String[] args) {
       /* Thread t = new Thread(new InterruptExplore().new RunThread());
        t.start();
        t.interrupt();*/
        Thread t = new Thread(new InterruptExplore().new RunThread1());
        t.start();

    }


    public class RunThread implements  Runnable{
        @Override
        public void run() {
            while (Thread.interrupted()){
                System.out.println("running ....");
            }
        }
    }

    public class RunThread1 implements  Runnable{
        @Override
        public void run() {

            /**
             * interrupted静态方法会清除状态位 即 第一次调用为true 则第二次调用会变为false
             * isInterrupted 则不会清楚状态位
             */
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("running ....");
                Thread.currentThread().interrupt();
            }
        }
    }
}
