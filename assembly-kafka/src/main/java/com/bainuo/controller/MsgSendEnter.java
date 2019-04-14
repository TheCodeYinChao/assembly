package com.bainuo.controller;

import com.bainuo.kafka.KafkaSender;
import com.bainuo.runtest.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 2019/4/13.
 */
@RequestMapping("/send")
@Slf4j
@RestController
public class MsgSendEnter {
    @Autowired
    private Producer producer;
    @Autowired
    private KafkaSender sender;

    @GetMapping("/msg")
    public void sendMsg(){
        log.info("seng msg enter");
        producer.send();

    }
    @GetMapping("/msg1")
    public void sendMsg1(){
        log.info("seng msg enter");
        sender.send();

    }
}
