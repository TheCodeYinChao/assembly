package com.raydata.test.config.common.config;

import org.springframework.amqp.core.Message;

/**
 * 消息处理基类
 */
public interface MessageListenerDataService {
    public void handleMsg(Message msg)throws  Exception;
    public String getQueue();
}
