package com.raydata.test.config.common.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * Rabbit监听器
 */
@Slf4j
public class RabbitListener implements ChannelAwareMessageListener {

	long tag = 0;
	Action action = Action.ACCEPT;

	private MessageListenerDataService messageDataService;

	public RabbitListener(MessageListenerDataService messageDataService) {
		super();
		this.messageDataService = messageDataService;
	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		if (message == null) {
			return;
		}
		try {
			log.info("[rabbit receive]body:{}", new String(message.getBody()));
			messageDataService.handleMsg(message);
		} catch (Exception e) {
			e.printStackTrace();
			action = Action.RETRY;
			log.error("[rabbit receive]Exception:{}", e.getMessage());
		}finally {
			try {
				if (action == Action.ACCEPT) {
					channel.basicAck(tag, true);
				} else if (action == Action.RETRY) {
					/**删除队列头信息 加入队列尾部*/
					channel.basicNack(tag, false, false);
					channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
							message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
							message.getBody());
					action = Action.ACCEPT;
				} else {
					/**拒绝消息也相当于主动删除mq队列的消息*/
					channel.basicNack(tag, false, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}




	enum Action {
		ACCEPT,  // 处理成功
		RETRY,   //删除头 往对列尾部添加
		REJECT,  // 无需重试的错误
	}
}
