package com.bainuo.rabbit.consume;


import com.bainuo.rabbit.factory.RabbitContainerFactory;
import com.bainuo.rabbit.listenter.RabbitListener;
import com.bainuo.service.impl.MqListenerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = "${}")
@Slf4j
public class Consumer {

    @RabbitHandler
    public void handleMsg(String msg){

    }


    @Autowired
    ConnectionFactory connectionFactory;
    @Autowired
    RabbitContainerFactory rabbitContainerFactory;
    @Autowired
    MqListenerServiceImpl mqListenerServiceImpl;


    /** Rabbit MQ消息监听 */
    @Bean
    public SimpleMessageListenerContainer noticeListener() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory); // 读取默认配置
        container.setQueues(new Queue("")); // 设置队列
        container.setExposeListenerChannel(true);// 注册 channel 监听
        container.setMaxConcurrentConsumers(1); // 设置最大并发消费者数
        container.setConcurrentConsumers(1);// 设置 并发消费者数 并发消费者不能大于 最大并发消费者
        container.setAcknowledgeMode(AcknowledgeMode.AUTO); // 设置确认模式自动确认
        container.setPrefetchCount(1); // 设置每次分配消息数最大值
        container.setMessageListener(new ChannelAwareMessageListener() {

            @Override
            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {

            }
        });

        return container;
    }
    @Bean
    public SimpleMessageListenerContainer monitorContainer() {
        String[] queueNames = { "nihao" };
        RabbitListener rabbitListener = new RabbitListener(mqListenerServiceImpl);
        SimpleMessageListenerContainer container = null;
        try {
            container = rabbitContainerFactory.createContainer(rabbitListener, queueNames);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[monitor listenerContainer]Exception:{}", e.getMessage());
        }
        return container;
    }



}
