package com.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.model.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description template要显示配置messageConvert为json转换器
 * @date 2018/07/27 10:44
 */
@Service
public class SendJsonClient implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{
	@Autowired
	@Qualifier("rabbitJsonTemplate")
	private RabbitTemplate rabbitJsonTemplate;

	@PostConstruct
	public void init() {
		rabbitJsonTemplate.setConfirmCallback(this);
		rabbitJsonTemplate.setReturnCallback(this);
		rabbitJsonTemplate.setMandatory(true);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if(ack) {
			System.out.println("JSON消息发送成功:" + correlationData);
		}else {
			System.out.println("JSON消息发送失败:" + cause);
		}
	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println(message.getMessageProperties().getCorrelationId() + " JSON消息发送失败");
	}

	public void sendJsonString(String exchange, String routingKey, Object o) {
		/**
		 * 设置过template的convert为Jackson2JsonMessageConverter后，
		 * 下面显示将对象转为JSON字符串的操作与底层转换操作重复了，意义不大
		 *
			 MessageProperties mp = new MessageProperties();
			 mp.setContentType(MessageProperties.CONTENT_TYPE_JSON);
			 String jsonStr = JSON.toJSONString(o);
			 Message msg = new Message(jsonStr.getBytes(), mp);
			 rabbitJsonTemplate.convertAndSend(exchange, routingKey, msg);
		 ***/
		/* 由于接收端只能将队列中数据转换为一种对象，因此此处使用Object类型意义不大，可以直接使用对象类型*/
		rabbitJsonTemplate.convertAndSend(exchange, routingKey, o);

		/* 无意义的信息，接收端不响应不消费 */
//		rabbitJsonTemplate.convertAndSend(exchange, routingKey, "1111");
	}


}
