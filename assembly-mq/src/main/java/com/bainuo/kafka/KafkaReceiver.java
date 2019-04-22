package com.bainuo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2019/4/13.
 */
@Component
@Slf4j
public class KafkaReceiver {
    @KafkaListener(topics = {"zhisheng"})
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();

            log.info("----------------- record =" + record);
            log.info("------------------ message =" + message);
        }

    }
   @KafkaListener(topics = {"${topicName.topic2}"},containerFactory = "batchFactory")
    public void listens(List<ConsumerRecord<String, String>> records) {
        for(ConsumerRecord<String, String> record : records){
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());

            if (kafkaMessage.isPresent()) {

                Object message = kafkaMessage.get();

                log.info("----------------- record =" + record);
                log.info("------------------ message =" + message);
            }
        }

    }
}
