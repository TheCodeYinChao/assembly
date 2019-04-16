package com.netty.future.listener;

import java.util.Collection;
import java.util.concurrent.*;

/**
 * Created by Admin on 2019/4/15.
 */
public abstract class AbstractFuture <T> implements IFuture<T> {
    protected volatile Object result;

    protected Collection<IFutureListener<T>> listeners = new CopyOnWriteArrayList<IFutureListener<T>>();

    private static final SuccessSignal SUCCESS_SIGNAL = new SuccessSignal();

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if(isDone()){
            return false;
        }
        synchronized (this){
            if(isDone()){
                return false;
            }
            result = new CauseHolder(new CancellationException());
            notifyAll();
        }
//        notifyListeners();
        return true;
    }



    @Override
    public boolean isCancellable() {
        return result == null;
    }

    @Override
    public boolean isCancelled() {
        return result!=null&&result instanceof CauseHolder && ((CauseHolder)result).cause instanceof CancellationException;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        await();
        Throwable cause = cause();
        if(cause == null){
            return getNow();
        }
        if(cause instanceof CancellationException){
            throw (CancellationException)cause;
        }
        throw new ExecutionException(cause);
    }



    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException,ExecutionException,TimeoutException{
        if(await(timeout,unit)){// 超时等待执行结果  
            Throwable cause = cause();
        if(cause == null){// 没有发生异常，异步操作正常结束  
            return getNow();
        }
        if(cause instanceof CancellationException){// 异步操作被取消了  
            throw (CancellationException)cause;
        }
            throw new ExecutionException(cause);// 其他异常  
        }
        // 时间到了异步操作还没有结束, 抛出超时异常  
        throw new TimeoutException();
    }
    @Override
    public boolean isSuccess() {
        return result == null ? false : !(result instanceof CauseHolder);
    }

    @Override
    public T getNow() {
        return (T) (result == SUCCESS_SIGNAL ? null : result);
    }
    @Override
    public Throwable cause(){
        if(result != null && result instanceof CauseHolder) {
//            return ((CauseHolder) result).cause;/**/
        }
        return null;
    }
}
