package com.raydata.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * description: Service <br>
 * date: 2020/6/16 18:41 <br>
 * author: zyc <br>
 * version: 1.0 <br>
 */
@Component
public class Service implements CommandLineRunner {

    @Autowired
    private WrapRabbitTemplate rabbitTemplate;
    @Override
    public void run(String... args) throws Exception {

        rabbitTemplate.sendMsg(RmqConstant.ORDER_LOG.getExchange(),RmqConstant.ORDER_LOG.getQueueName(),"这是个测试消息");

    }
}
