package com.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 队列配置，可以配置多个队列
 * 				提取出来主要为了扩展方便，简单场景直接放到RabbitMqConfig
 * @date 2018/07/18 20:27
 */
@Configuration
public class QueueConfig {
	/*对列名称*/
	public static final String QUEUE_01_NAME = "first-queue";
	public static final String QUEUE_02_NAME = "second-queue";
	public static final String QUEUE_03_NAME = "third-queue";

	private Queue queueOne = null;
	private Queue queueTwo = null;
	private Queue queueThree = null;

	public Queue firstQueue() {
		/**
		 durable="true" 持久化消息队列 ， rabbitmq重启的时候不需要创建新的队列
		 auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
		 exclusive  表示该消息队列是否只在当前connection生效,默认是false
		 */
		if(queueOne == null) {
			synchronized (this) {
				if(queueOne == null) {
					queueOne = new Queue(QUEUE_01_NAME,true,false,false);
				}
			}
		}
		return queueOne;
	}

	public Queue secondQueue() {
		if(queueTwo == null) {
			synchronized (this) {
				if(queueTwo == null) {
					queueTwo = new Queue(QUEUE_02_NAME,true,false,false);
				}
			}
		}
		return queueTwo;
	}

	public Queue thirdQueue() {
		if(queueThree == null) {
			synchronized (this) {
				if(queueThree == null) {
					// 配置 自动删除
					Map<String, Object> arguments = new HashMap<>();
					arguments.put("x-message-ttl", 6000000);//60秒自动删除
					queueThree = new Queue(QUEUE_03_NAME,true,false,true, arguments);
				}
			}
		}
		return queueThree;
	}
}
