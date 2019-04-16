package com.netty.future.listener;

public class CauseHolder {
    public Throwable cause;

    public CauseHolder(Throwable cause) {
        this.cause = cause;
    }

    public CauseHolder() {

    }
}