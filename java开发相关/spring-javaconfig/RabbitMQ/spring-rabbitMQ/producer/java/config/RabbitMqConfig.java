package com.rabbitmq.config;

import com.rabbitmq.mqcallback.MsgSendConfirmCallBack;
import com.rabbitmq.mqcallback.MsgSendReturnCallback;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description RabbitMq配置
 * @date 2018/07/18 20:29
 */
@Configuration
@ComponentScan({"com.rabbitmq.service", "com.rabbitmq.demo"})
@PropertySource("classpath:/config.properties")
public class RabbitMqConfig {

	/**
	 * key: queue在该direct-exchange中的key值，当消息发送给direct-exchange中指定key为设置值时，
	 * 消息将会转发给queue参数指定的消息队列
	 */
	/** 队列key1*/
	public static final String ROUTING_KEY_1 = "queue_one_key1";
	public static final String ROUTING_KEY_2 = "queue_one_key2";

	@Autowired
	private QueueConfig queueConfig;

	@Autowired
	private ExchangeConfig exchangeConfig;

	@Value("${rabbitmq.host}")
	private String host;

	@Value("#{T(java.lang.Integer).parseInt('${rabbitmq.port}')}")
	private int port;

	@Value("${rabbitmq.username}")
	private String username;

	@Value("${rabbitmq.password}")
	private String password;


	@Bean
	public ConnectionFactory getConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(host, port);
		factory.setUsername(username);
		factory.setPassword(password);
		// 配合相应的confirm-callback，必须显示设置为true
		factory.setPublisherConfirms(true);
		// 配合相应的return-callback，必须显示的设置为true
		factory.setPublisherReturns(true);
		return factory;
	}

	@Bean
	public RabbitAdmin getRabbitAdmin(ConnectionFactory factory) {
		RabbitAdmin admin = new RabbitAdmin(factory);

		/* 此处要先声明Exchange和Queue，存在时无影响，不存在时后面的binding操作会报错 */
		admin.declareExchange(exchangeConfig.directExchangeOne());
		admin.declareQueue(queueConfig.firstQueue());
		Binding bindingOne = BindingBuilder.bind(queueConfig.firstQueue())
				.to(exchangeConfig.directExchangeOne())
				.with(RoutingKeyConstant.DIRECT_ROUTING_KEY_1);
		admin.declareBinding(bindingOne);

		admin.declareQueue(queueConfig.secondQueue());
		Binding bindingTwo = BindingBuilder.bind(queueConfig.secondQueue())
				.to(exchangeConfig.directExchangeOne())
				.with(RoutingKeyConstant.DIRECT_ROUTING_KEY_2);
		admin.declareBinding(bindingTwo);


		admin.declareExchange(exchangeConfig.topicExchangeOne());
		admin.declareQueue(queueConfig.thirdQueue());
		Binding bindingThree = BindingBuilder.bind(queueConfig.thirdQueue())
				.to(exchangeConfig.topicExchangeOne())
				.with(RoutingKeyConstant.TOPIC_ROUTING_KEY_1);
		admin.declareBinding(bindingThree);

		return admin;
	}

	@Bean("rabbitJsonTemplate")
	public RabbitTemplate rabbitJsonTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate  template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		return template;
	}


	/**
	 * 定义rabbit template用于数据的接收和发送
	 * 可以设置消息确认机制和回调
	 * @return
	 */
	@Bean("rabbitTemplate")
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		// 可以自定义消息转换器  默认使用的JDK的SimpleMessageConverter，所以消息对象需要实现Serializable
		// template.setMessageConverter();


		/**若使用confirm-callback或return-callback，
		 * 必须要配置publisherConfirms或publisherReturns为true
		 * 每个rabbitTemplate只能有一个confirm-callback和return-callback
		 */
		template.setConfirmCallback(msgSendConfirmCallBack());

		/**
		 * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
		 * 可针对每次请求的消息去确定’mandatory’的boolean值，
		 * 只能在提供’return -callback’时使用，与mandatory互斥
		 */
		template.setReturnCallback(msgSendReturnCallback());
		template.setMandatory(true);

		return template;
	}


	/**
	 * 关于 msgSendConfirmCallBack 和 msgSendReturnCallback 的回调说明：
	 * 1.如果消息没有到exchange,则confirm回调,ack=false
	 * 2.如果消息到达exchange,则confirm回调,ack=true
	 * 3.exchange到queue成功,则不回调return
	 * 4.exchange到queue失败,则回调return(需设置mandatory=true,否则不回调,消息就丢了)
	 */

	/**
	 * 消息确认机制
	 * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
	 * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
	 * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)
	 * 在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
	 * @return
	 */
	public MsgSendConfirmCallBack msgSendConfirmCallBack(){
		return new MsgSendConfirmCallBack();
	}

	public MsgSendReturnCallback msgSendReturnCallback(){
		return new MsgSendReturnCallback();
	}
}
