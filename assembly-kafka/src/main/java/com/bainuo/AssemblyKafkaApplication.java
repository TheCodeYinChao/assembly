package com.bainuo;

import com.bainuo.kafka.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

@SpringBootApplication
public class AssemblyKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssemblyKafkaApplication.class, args);
	}

}
