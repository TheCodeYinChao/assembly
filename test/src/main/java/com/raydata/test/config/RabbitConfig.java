package com.raydata.test.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: AppConfig <br>
 * date: 2020/6/10 15:09 <br>
 * author: zyc <br>
 * version: 1.0 <br>
 */
@EnableRabbit
@Configuration
public class RabbitConfig implements InitializingBean {
    public static final  String MQ_DEFAULT_FACTORY = "rabbitListenerContainerFactory";

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(5);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO); //这里控制着全局的是否自动确认
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;

    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Override
    public void afterPropertiesSet() throws Exception {
        RmqConstant[] values = RmqConstant.values();
        for (RmqConstant value : values) {
            String exchange = value.getExchange();
            String queueName = value.getQueueName();
            Queue queue = new Queue(queueName);
            DirectExchange directExchange = new DirectExchange(exchange);
            Binding binding = BindingBuilder.bind(queue).to(directExchange)
                    .with(queueName);
            rabbitAdmin.declareExchange(directExchange);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);
        }
    }

}
