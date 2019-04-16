package com.netty.future.listener;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 2019/4/15.
 */
public interface IFuture<T> extends Future<T> {
    boolean isSuccess();
    T getNow();
    Throwable cause();
    boolean isCancellable(); //是否可以取消
    IFuture<T> await() throws InterruptedException;
    boolean await(long timeOut)throws InterruptedException;
    boolean await(long timeOut, TimeUnit timeUnit)throws InterruptedException;
    IFuture<T> awaitUnInterruptibly() throws InterruptedException;
    IFuture<T> awaitUnInterruptibly(long timeoutMillis) throws InterruptedException;
    IFuture<T> awaitUnInterruptibly(long timeoutMillis, TimeUnit timeUnit) throws InterruptedException;
    IFuture<T> addListener(IFutureListener<T> iFutureListener );
    IFuture<T> removeListener(IFutureListener<T> iFutureListener );


}
