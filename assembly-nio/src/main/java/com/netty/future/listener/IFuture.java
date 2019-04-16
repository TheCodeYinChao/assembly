package com.netty.future.listener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface IFuture<V> extends Future<V> {

    public boolean isSuccess(); //是否成功

    public V getNow();

    public boolean isCancellable(); //是否可以取消


    public IFuture<V> await() throws InterruptedException, ExecutionException;//等待future完成
    public boolean await(long timeOutMills) throws InterruptedException; //等待future超时完
    public boolean await(long timeOut, TimeUnit timeUnit) throws InterruptedException; //等待future超时完成
    public IFuture<V> awaitUninterruptibly() throws Exception;   //等待future完成，不相应中断
    public boolean awaitUninterruptibly(long timeOutMills);
    public boolean awaitUninterruptibly(long timeOut, TimeUnit timeUnit);


    public  IFuture<V> addListener(IFutureListener future);

    public  IFuture<V> removeListener(IFuture<V> future);



}
