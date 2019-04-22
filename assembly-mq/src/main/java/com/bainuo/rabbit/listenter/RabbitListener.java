package com.bainuo.rabbit.listenter;

import com.bainuo.service.MqListenerService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class RabbitListener implements MessageListener {
    private MqListenerService mqListenerService;

    public RabbitListener(MqListenerService mqListenerService) {
        this.mqListenerService = mqListenerService;
    }


    @Override
    public void onMessage(Message message) {
        mqListenerService.data(message.toString());
    }
}
