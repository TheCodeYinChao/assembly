package com.bainuo.kafka;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;
import java.util.Random;

/**
 * Created by Admin on 2019/4/13.
 */
@Component
@Slf4j
public class KafkaSender  implements ListenableFutureCallback<SendResult<String, String>>{

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    //发送消息方法
    public void send() {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(new Random().nextInt()+"");
        message.setSendTime(new Date());
        log.info("+++++++++++++++++++++  message = {}", message);
        ListenableFuture<SendResult<String, String>> zhisheng = kafkaTemplate.send("zhisheng", message + "");
        zhisheng.addCallback(this);
    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onSuccess(SendResult<String, String> stringStringSendResult) {
        System.out.println(JSON.toJSONString(stringStringSendResult));

    }
}
