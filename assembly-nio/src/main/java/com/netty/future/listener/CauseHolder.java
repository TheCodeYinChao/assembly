package com.netty.future.listener;

import java.util.concurrent.CancellationException;

/**
 * Created by Admin on 2019/4/15.
 */
public class CauseHolder {
    public Object cause;

    public CauseHolder(CancellationException e) {
    }
}
