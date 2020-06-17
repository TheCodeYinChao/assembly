package com.raydata.test.config.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 监听容器创建工厂
 */
@Slf4j
@Component
@Deprecated
public class RabbitContainerFactory {

	/** rabbit conn */
	@Autowired
    ConnectionFactory connectionFactory;

	/**
	 * 创建监听容器手动应答
	 * rabbitListener:监听器
	 * queueNames:队列名称
	 */
	public SimpleMessageListenerContainer createContainer(RabbitListener rabbitListener, String... queueNames)
			throws Exception {
		if (rabbitListener == null) {
			throw new InterruptedException("messageListener is null.");
		}
		log.info("[rabbit listener]queueName:{}", queueNames.toString());

		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueNames);
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setMessageListener(new MessageListenerAdapter(rabbitListener));
		return container;
	}

}
