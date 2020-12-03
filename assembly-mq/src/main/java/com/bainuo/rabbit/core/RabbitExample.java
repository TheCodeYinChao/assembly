package com.bainuo.rabbit.core;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
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

        channel.exchangeDeclare(exchange,BuiltinExchangeType.DIRECT,true,false,null);
        channel.queueDeclare(queue,true,false,false,null);
        String rountkey="a";
        channel.queueBind(queue,exchange,rountkey,null);
        for (int i = 0; i < 100000; i++) {
            channel.basicPublish(exchange,rountkey, MessageProperties.PERSISTENT_TEXT_PLAIN,("nihao:"+i).getBytes());
        }
        System.in.read();
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
        /**
         * 我们一台消费者客户端，1分钟同时要接收到300条消息，已经超过我们最大的负载，这时，就可能导致，服务器资源被耗尽，消费者客户端卡死等情况。
         * RabbitMQ提供了一种qos（服务质量保证）功能，即在非自动确认消息的前提下，如果一定数目的消息（通过基于consume或者channel设置Qos的值）未被确认前，不进行消费新的消息
         * 服务器投递30 我们每秒处理一个则会出现30个尚未确认
         */
        channel.basicQos(30);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println(new String (body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(queue,consumer);
        System.in.read();
        channel.close();
        connection.close();

    }


    @Test
    public void produceRabbitTransaction() throws IOException, TimeoutException {
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

        channel.exchangeDeclare(exchange,BuiltinExchangeType.DIRECT,true,false,null);
        channel.queueDeclare(queue,true,false,false,null);
        String rountkey="a";
        channel.queueBind(queue,exchange,rountkey,null);
        for (int i = 0; i < 100000; i++) {
            try {
                channel.txSelect();//开启事务
                //deliveryMode =2 时则会持久化消息
                channel.basicPublish(exchange,rountkey, MessageProperties.PERSISTENT_TEXT_PLAIN,("nihao:"+i).getBytes());
                channel.txCommit();//提交事务
            } catch (IOException e) {
                e.printStackTrace();
                channel.txRollback();//异常回滚
            }
        }
        System.in.read();
        channel.close();
        connection.close();

    }
}
