package com.bainuo.rabbit.factory;

import com.bainuo.rabbit.listenter.RabbitListener;
import com.bainuo.service.impl.MqListenerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitContainerFactory {

    /** rabbit conn */
    @Autowired
    ConnectionFactory connectionFactory;
    @Autowired
    MqListenerServiceImpl mqListenerServiceImpl;

    /**
     * 创建监听容器<br>
     * rabbitListener:监听器<br>
     * queueNames:队列名称<br>
     */
    public SimpleMessageListenerContainer createContainer(RabbitListener rabbitListener, String... queueNames)
            throws Exception {
        if (rabbitListener == null) {
            throw new InterruptedException("messageListener is null.");
        }
        log.info("[rabbit listener]queueName:{}", queueNames.toString());

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueNames);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        RabbitListener r = new RabbitListener(mqListenerServiceImpl);
        container.setMessageListener(new MessageListenerAdapter(r));
        return container;
    }
}
