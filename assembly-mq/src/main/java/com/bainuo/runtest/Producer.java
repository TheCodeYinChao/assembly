package com.bainuo.runtest;

import com.alibaba.fastjson.JSON;
import com.bainuo.kafka.producer.KafkaProducer;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

/**
 * Created by Admin on 2019/4/13.
 */
@Component
@Slf4j
public class Producer {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Value("${topicName.topic2}")
    private String tipic;
    public void send(){
        List<String> json = Lists.newArrayList();

        for(int i=0;i<10;i++){
            String msg = "发送的消息 第 "+i+"条";
            ListenableFuture send = kafkaTemplate.send(tipic, msg);
            send.addCallback(new ListenableFutureCallback() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.info(throwable.getMessage(),throwable);
                }

                @Override
                public void onSuccess(Object o) {
                    log.info(JSON.toJSONString(o));
                }
            });
        }
        log.info("send end");
    }
}
