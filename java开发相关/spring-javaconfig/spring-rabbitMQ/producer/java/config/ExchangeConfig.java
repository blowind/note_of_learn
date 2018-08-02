package com.rabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 交换器相关的配置，可配置多个交换器，
 * 				提取出来主要为了扩展方便，简单场景直接放到RabbitMqConfig
 * @date 2018/07/18 20:04
 */
@Configuration
public class ExchangeConfig {

	/** 直接消息交换机1的名字*/
	public final String DIRECT_EXCHANGE_01 = "first_direct_exchange";

	/** 主题消息交换机2的名字*/
	public final String TOPIC_EXCHANGE_01 = "first_topic_exchange";

	private DirectExchange directExchangeOne = null;

	private TopicExchange topicExchangeOne = null;

	/**
	 *   1.定义direct exchange，绑定first_exchange
	 *   2.durable="true" 持久化交换机， rabbitmq重启的时候不需要创建新的交换机
	 *   3.direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
	 *     fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
	 *     topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
	 */
	public DirectExchange directExchangeOne(){
		if(directExchangeOne == null) {
			synchronized (this) {
				if(directExchangeOne == null) {
					directExchangeOne = new DirectExchange(DIRECT_EXCHANGE_01,true,false);
				}
			}
		}
		return directExchangeOne;
	}

	public TopicExchange topicExchangeOne() {
		if(topicExchangeOne == null) {
			synchronized (this) {
				if(topicExchangeOne == null) {
					topicExchangeOne = new TopicExchange(TOPIC_EXCHANGE_01, true, false);
				}
			}
		}
		return topicExchangeOne;
	}
}
