package com.raydata.test.config.common.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * description: InitListenerContaire <br>
 * date: 2020/6/17 14:18 <br>
 * author: zyc <br>
 * version: 1.0 <br>
 */
@Configuration
public class InitListenerContainer implements ApplicationContextAware,InitializingBean {

    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private ConnectionFactory connectionFactory;


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, MessageListenerDataService> beansOfType = applicationContext.getBeansOfType(MessageListenerDataService.class);
        for(Map.Entry<String,MessageListenerDataService> entry: beansOfType.entrySet()){
            MessageListenerDataService y = entry.getValue();
            RabbitListener rabbitListener = new RabbitListener(y);

            String beanName = y.getQueue()+"MessageListenerContainer";
            //Bean的实例工厂
            DefaultListableBeanFactory dbf = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
            //Bean构建  BeanService.class 要创建的Bean的Class对象
            BeanDefinitionBuilder dataSourceBuider = BeanDefinitionBuilder. genericBeanDefinition(SimpleMessageListenerContainer.class);
            dbf.registerBeanDefinition(beanName, dataSourceBuider.getBeanDefinition());
            dataSourceBuider.addPropertyValue("connectionFactory", connectionFactory);
            /**
             * 这里 获取 smlc  如果没有则会通过doCreateBean 进行创建 来解决 繁琐的@bean的注入问题
             */
            SimpleMessageListenerContainer container=(SimpleMessageListenerContainer) applicationContext.getBean(beanName);//
            container.setQueueNames(y.getQueue());
            container.setExposeListenerChannel(true);
            container.setMaxConcurrentConsumers(5);
            container.setConcurrentConsumers(5);
            container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            container.setMessageListener(new MessageListenerAdapter(rabbitListener));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext)applicationContext;
    }

}
