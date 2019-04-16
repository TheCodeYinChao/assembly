package com.netty.future.listener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by jiangwenping on 16/12/27.
 */
//只是把两个方法对外暴露
public class DelayAdditionFuture extends AbstractFuture<Integer> {


    @Override
    public IFuture<Integer> setSuccess(Object result) {
        return super.setSuccess(result);
    }

    @Override
    public IFuture<Integer> setFailure(Throwable cause) {
        return super.setFailure(cause);
    }

}
