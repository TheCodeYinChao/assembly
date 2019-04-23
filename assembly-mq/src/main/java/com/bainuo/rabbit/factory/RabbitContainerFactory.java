package com.bainuo.rabbit.factory;

import com.bainuo.rabbit.listenter.RabbitListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitContainerFactory {


    /**
     *Rabbitmq connectionFactory 自定义
     * @return
     */
    @Bean("connectionFactory")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("rabbitmq.addresses");
        connectionFactory.setUsername("rabbitmq.username");
        connectionFactory.setPassword("rabbitmq.password");
        connectionFactory.setVirtualHost("rabbitmq.xmslog.vhost");
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }


    @Bean
    public ConnectionFactory xmsFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("spring.rabbitmq.address");
        connectionFactory.setUsername("spring.rabbitmq.username");
        connectionFactory.setPassword("spring.rabbitmq.password");
        connectionFactory.setVirtualHost("spring.rabbitmq.virtualHost");/*注意选择虚拟机对上面的不同*/
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }

    @Bean(name="xmsTemplate")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate xmsTemplate() {
        RabbitTemplate template = new RabbitTemplate(xmsFactory());
        return template;
    }

    public SimpleMessageListenerContainer createContainer(RabbitListener rabbitListener, String[] queueNames) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory()); // 读取默认配置
        Queue [] queues = {new Queue(queueNames[0]),new Queue(queueNames[1])};
        container.setQueues(queues); // 设置队列
        container.setExposeListenerChannel(true);// 注册 channel 监听
        container.setMaxConcurrentConsumers(1); // 设置最大并发消费者数
        container.setConcurrentConsumers(1);// 设置 并发消费者数 并发消费者不能大于 最大并发消费者
        container.setAcknowledgeMode(AcknowledgeMode.AUTO); // 设置确认模式自动确认
        container.setPrefetchCount(1); // 设置每次分配消息数最大值
        container.setMessageListener(rabbitListener);
        return container;
    }
}
