package com.netty.future.listener;

/**
 * Created by jiangwenping on 16/12/27.
 */
public interface IFutureListener<V extends IFuture<?>>{

    /**
     *  完成
     * @param future
     * @throws Exception
     */
    void operationComplete(IFuture<V> future) throws Exception;
}
