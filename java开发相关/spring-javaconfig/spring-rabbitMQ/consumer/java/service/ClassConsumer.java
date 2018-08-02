package com.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 整个类作为RabbitListener，通过RabbitHandler处理消息
 * @date 2018/07/26 14:55
 */
@Component
@RabbitListener(queues = "second-queue")
public class ClassConsumer {

	/* 匹配生产者指定ContentType为 MessageProperties.CONTENT_TYPE_TEXT_PLAIN 类型的消息 */
	@RabbitHandler
	public void processString(String msg) {
		System.out.println("String message: " + msg);
	}

	/* 匹配生产者指定ContentType为 MessageProperties.CONTENT_TYPE_BYTES 类型的消息 */
	@RabbitHandler
	public void processBytes(byte[] bytes) {
		System.out.println("bytes message: " + new String(bytes));
	}
}
