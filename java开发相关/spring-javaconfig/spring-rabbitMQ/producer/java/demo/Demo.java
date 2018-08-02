package com.rabbitmq.demo;

import com.rabbitmq.config.ExchangeConfig;
import com.rabbitmq.config.RoutingKeyConstant;
import com.rabbitmq.model.City;
import com.rabbitmq.model.User;
import com.rabbitmq.service.SendClient;
import com.rabbitmq.service.SendJsonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/07/18 21:01
 */
@Component
public class Demo implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private SendClient sendClient;
	@Autowired
	private SendJsonClient sendJsonClient;

	@Autowired
	private ExchangeConfig exchangeConfig;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent e) {

		String flag = "json";

		if("json".equals(flag)) {
			User user = new User();
			user.setId(1);
			user.setName("wahaha");
			user.setAge(3);
			user.setAddress("Hangzhou, Zhejiang Province");
			sendJsonClient.sendJsonString(exchangeConfig.TOPIC_EXCHANGE_01,
					RoutingKeyConstant.TOPIC_ROUTING_KEY_1,
					user);

			City city = new City();
			city.setName("Nanjing");
			city.setLocation("Jiangsu Province");

			sendJsonClient.sendJsonString(exchangeConfig.TOPIC_EXCHANGE_01,
					RoutingKeyConstant.TOPIC_ROUTING_KEY_1,
					city);

		}else if("string".equals(flag)) {
			String msg = "test first queue";

			MessageProperties mp = new MessageProperties();
			mp.setAppId("Customer RabbitMQ APP");
			mp.setClusterId("MyCluster One");
			mp.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
			mp.setContentEncoding("UTF-8");
			mp.setContentLength(msg.length());
//		mp.setDeliveryMode(MessageProperties.DEFAULT_DELIVERY_MODE);
//		mp.setPriority(MessageProperties.DEFAULT_PRIORITY);
			mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
			mp.setPriority(3);
			mp.setCorrelationId(UUID.randomUUID().toString());
			mp.setReplyTo("Customer ReplyTo");
			mp.setReceivedUserId("Customer Receive UserId");
			mp.setDelay(300);

			System.out.println(msg);
			Message message = new Message(msg.getBytes(), mp);
			sendClient.sendMessageInString(exchangeConfig.DIRECT_EXCHANGE_01,
					RoutingKeyConstant.DIRECT_ROUTING_KEY_2,
					message);
		}
	}
}
