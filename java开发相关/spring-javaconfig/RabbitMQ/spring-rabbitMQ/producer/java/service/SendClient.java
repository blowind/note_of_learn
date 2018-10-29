package com.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.model.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 消息发送者
 * @date 2018/07/18 20:51
 */
@Service
public class SendClient {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendString(String info, String exchange, String routingKey) {
		String msgId = UUID.randomUUID().toString();
		/**
		 * 构建Message ,主要是使用 msgId 将 message 和 CorrelationData 关联起来。
		 * 这样当消息发送到交换机失败的时候，在 MsgSendConfirmCallBack 中就可以根据
		 * correlationData.getId()即 msgId,知道具体是哪个message发送失败,进而进行处理。
		 */
        /*将 msgId和 message绑定*/
		Message message = MessageBuilder.withBody(info.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON)
				.setCorrelationId(msgId).build();
        /*将 msgId和 CorrelationData绑定*/
		CorrelationData correlationData = new CorrelationData(msgId);

		// TODO 将 msgId 与 Message 的关系保存起来
		/**
		 * 将 msgId 与 Message 的关系保存起来,例如放到缓存中.
		 * 当 MsgSendReturnCallback回调时（消息从交换机到队列失败）,进行处理 {@code MsgSendReturnCallback}.
		 * 当 MsgSendConfirmCallBack回调时,进行处理 {@code MsgSendConfirmCallBack}.
		 * 定时检查这个绑定关系列表,如果发现一些已经超时(自己设定的超时时间)未被处理,则手动处理这些消息.
		 */
		/**
		 * 发送消息
		 * 指定消息交换机  "first_exchange"
		 * 指定队列key    "queue_one_key1"
		 */
		rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
		System.out.println("发送消息：correlationData" + correlationData);
	}

	public void sendMessageInString(String exchange, String routingKey, Message message) {
		message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
		rabbitTemplate.send(exchange, routingKey, message);
	}

	public void sendMessageInBytes(String exchange, String routingKey, Message message) {
		message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_BYTES);
		rabbitTemplate.send(exchange, routingKey, message);
	}

	public void sendMessageInJson(String exchange, String routingKey, Message message) {
		message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
	}

	public void sendJsonString(String exchange, String routingKey, User user) {
		MessageProperties mp = new MessageProperties();
		mp.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		String jsonStr = JSON.toJSONString(user);
		Message msg = new Message(jsonStr.getBytes(), mp);
		rabbitTemplate.convertAndSend(exchange, routingKey, msg);
	}

}
