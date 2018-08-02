package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 配置程序，作为最简单的接受者不需要什么配置
 * @date 2018/07/19 10:16
 */
@Configuration
@EnableRabbit     //  此标记千万不能少，不然消费者不接收消息
public class RabbitMqConfig {

	@Bean
	public ConnectionFactory getConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory("47.75.50.16", 5672);
		factory.setUsername("admin");
		factory.setPassword("1qaz2wsx");
		return factory;
	}

	@Bean(value = "rabbitListenerContainerFactory")
	public RabbitListenerContainerFactory myFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setConcurrentConsumers(1);
		// 如何要求本地接收消息后处理失败的情况下不消费消息，则需要在此处显示设置为MANUAL，默认为NONE
		// 配合消息处理函数中的channel.basicNack调用
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		// 发送者指定了消息转换器时，接收者也要指定，默认是SimpleMessageConverter
		 factory.setMessageConverter(new Jackson2JsonMessageConverter());
		return factory;
	}

}
