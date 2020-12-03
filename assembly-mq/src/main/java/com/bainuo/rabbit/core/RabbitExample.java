package com.bainuo.rabbit.core;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zyc
 * @date 2020/12/3
 * @time 23:03
 * @description :
 */

public class RabbitExample {
    String queue ="aa";
    @Test
    public void produceRabbit() throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("192.168.196.3");
        cf.setPort(5672);
        cf.setUsername("admin");
        cf.setPassword("admin");
        cf.setVirtualHost("my_vhost");//虚拟隔离环境
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        /**持久化非自动删除*/
        String exchange = "a";
        String queue ="aa";
        channel.exchangeDeclare(exchange,"direct",true,false,null);
        channel.queueDeclare(queue,true,false,false,null);
        String rountkey="a";
        channel.queueBind(queue,exchange,rountkey,null);
        channel.basicPublish(exchange,rountkey, MessageProperties.PERSISTENT_TEXT_PLAIN,"nihao".getBytes());
        channel.close();
        connection.close();

    }
    @Test
    public void consumerRabbit() throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("192.168.196.3");
        cf.setPort(5672);
        cf.setUsername("admin");
        cf.setPassword("admin");
        cf.setVirtualHost("my_vhost");//虚拟隔离环境
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(30);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println(new String (body));

                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(queue,consumer);
        channel.close();
        connection.close();

    }
}
