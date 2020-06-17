package com.raydata.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * description: Service <br>
 * date: 2020/6/10 15:15 <br>
 * author: zyc <br>
 * version: 1.0 <br>
 */
@Component
@Slf4j
public class WrapRabbitTemplate {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static  final  int RETRY_COUNT = 3;

    private  int retryCount = 0;

    private boolean isOpenRetry = false; //是否开启重试


    public void sendMsg(String exchange, String routingKey, Object msg){
        try {

            retryCount = RETRY_COUNT;
            boolean ack;
            CorrelationData cd= new CorrelationData();
            rabbitTemplate.convertAndSend(exchange,routingKey, msg, cd);
             ack = cd.getFuture().get(10, TimeUnit.SECONDS).isAck();

            while (!ack && retryCount > 0){
                ack = retrySend(exchange, routingKey, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void sendMsg(boolean isOpenRetry,String exchange, String routingKey, Object msg){
        try {
            this.isOpenRetry = isOpenRetry;
            this.retryCount = RETRY_COUNT;

            boolean ack;
            CorrelationData cd= new CorrelationData();
            rabbitTemplate.convertAndSend(exchange,routingKey, msg, cd);
            ack = cd.getFuture().get(10, TimeUnit.SECONDS).isAck();

            while (this.isOpenRetry && !ack && retryCount > 0){
                ack = retrySend(exchange, routingKey, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean retrySend(String exchange, String routingKey, Object msg) throws InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        boolean ack;
        log.error("Msg Send Fail Start Retry......Count:{}",retryCount);

        CorrelationData cdR = new CorrelationData();
        rabbitTemplate.convertAndSend(exchange,routingKey, msg, cdR);
        ack = cdR.getFuture().get(10, TimeUnit.SECONDS).isAck();

        retryCount--;
        return ack;
    }
}
