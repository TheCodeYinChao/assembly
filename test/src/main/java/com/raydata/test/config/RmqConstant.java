package com.raydata.test.config;

public enum RmqConstant {
    ORDER_TRADE("order_trade_exchange", QueueName.ORDER_TRADE),
    RESOURCE_SHOPPING("resource_shopping_exchange", QueueName.RESOURCE_SHOPPING),
    MESSAGE_CENTER("message_center_exchange", QueueName.MESSAGE_CENTER),
    USER_SPACE("user_space_exchange", QueueName.USER_SPACE),
    ORDER_LOG("order_log_exchange", QueueName.ORDER_LOG),
    /**
     * 1.2.7v
     */
    PERSONAGE_MSG("personage_msg_center_exchange", QueueName.PERSONAGE_MSG_CENTER),


    TEST_EXCHANGE("TEST", QueueName.TEST),

    COMMENT_CENTER("comment_center_exchange", QueueName.COMMENT_CENTER);


    private String exchange;
    private String queueName;

    RmqConstant( String exchange, String queueName) {
        this.exchange = exchange;
        this.queueName = queueName;
    }

    public final String getExchange() {
        return exchange;
    }

    public final String getQueueName() {
        return queueName;
    }
}
