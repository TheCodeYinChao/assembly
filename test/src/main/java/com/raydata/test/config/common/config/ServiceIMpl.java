package com.raydata.test.config.common.config;

import com.raydata.test.config.QueueName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * description: ServiceIMpl <br>
 * date: 2020/6/17 14:39 <br>
 * author: zyc <br>
 * version: 1.0 <br>
 */
@Component
@Slf4j
public class ServiceIMpl implements MessageListenerDataService {
    @Override
    public void handleMsg(Message msg) throws Exception {
       log.info( "###############list"+msg.toString());
    }

    @Override
    public String getQueue() {
        return QueueName.ORDER_LOG;
    }
}
