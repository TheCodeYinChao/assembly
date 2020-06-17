package com.raydata.test.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * description: MqListenter <br>
 * date: 2020/6/16 18:46 <br>
 * author: zyc <br>
 * version: 1.0 <br>
 */
@Component
@Slf4j
public class MqListenter {


    /**
     * 监听示例 1
     * @param message
     * @param deliveryTag
     * @param channel
     */
    @RabbitListener(containerFactory=RabbitConfig.MQ_DEFAULT_FACTORY, queues = QueueName.USER_SPACE)
    public void process(@Payload Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        if (message != null) {
            byte[] body = message.getBody();
            try {
                String rs = MessageUtil.byte2Str(message.getBody());
                log.info("Msg Recevice1 : {}",rs);

                channel.basicAck(deliveryTag, true);//正常确认
            } catch (IOException e) {
                try {
                    /**删除队列头信息 加入队列尾部*/
                    channel.basicNack(deliveryTag, false, false);
                    channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                            message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                            message.getBody());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }else {
            /**拒绝消息也相当于主动删除mq队列的消息*/
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 监听示例二
     * @param message
     */
 /*   @RabbitListener(containerFactory = RabbitConfig.MQ_DEFAULT_FACTORY, queues = QueueName.ORDER_LOG)
    public void process(Message message) {
        if (message != null) {
            String rs = MessageUtil.byte2Str(message.getBody());
            log.info("Msg Recevice2 : {}",rs);
        }
    }*/
}
