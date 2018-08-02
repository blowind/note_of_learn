package com.rabbitmq.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.model.City;
import com.rabbitmq.model.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 消息接收者
 * @date 2018/07/19 10:18
 */
@Component
public class MethodConsumer {

	/*对列名称*/
	public final String QUEUE_NAME1 = "first-queue";

	/**
	 * 获取JSON数据然后转为对象
	 * 此处接收端接收数据有三点要求：
	 * 1、接收端本地MessageConverter设置为Jackson2JsonMessageConverter
	 * 2、发送端设置ContentType为application/json，
	 *    不在乎是通过template设置Jackson2JsonMessageConverter的隐性设置
	 *    还是具体消息发送前MessageProperties属性的显性设置(使用默认jdk convert，通过JSON库将对象转换成json格式字符串发出)
	 * 3、payload段（实质是byte[]数组）的内容与接收对象的JSON格式一致
	 *
	 * 传递的JSON字符串与User字段内容对不上时，底层仅匹配名字类型相同的字段，其余字段置为null
	 * 传递的非JSON格式字符串时，函数不会被回调，不做任何动作
	 */
	@RabbitListener(queues = "third-queue")
	public void handleJson(@Payload User user, Message message, Channel channel) {
		System.out.println("===========in user===========");
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getAge());
		System.out.println(user.getAddress());
		System.out.println(user);

		try{
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * JSON映射接口之间彼此不能共存，因此一个queue只能做一个类对象的转换，
	 * 下面的写法会掩盖上面的User处理函数，导致该函数永远不会回调；
	@RabbitListener(queues = "third-queue")
	public void handleJson(@Payload City city) {
		System.out.println("===========in city===========");
		System.out.println(city.getName());
		System.out.println(city.getLocation());
		System.out.println(city);
	}
	 **/

	/**
	 * 通过Message类型能拿到全部所需的信息
	 * 通过Channel类型能拿到传输的信道对象
	 * 通过@Payload 注解能拿到String类型的纯字符内容
	 * 通过@Headers 注解能拿到全部信息头Map，其中大部分内容与message中MessageProperties对象存储内容一样
	 * 通过@Header  注解能拿到具体某个信息头的内容，名字通过注解字符串或者直接通过变量名指定
	 *
	 * **/
	@RabbitListener(queues = "first-queue")
	public void handleMessage(Message message, Channel channel, @Payload String body, @Headers Map<String, Object> headers, @Header("timestamp") Date timestamp, @Header String amqp_receivedExchange){
		System.out.println("【IN HANDLE MESSAGE】");

		MessageProperties mp = message.getMessageProperties();
		System.out.println("app-id: " + mp.getAppId());
		System.out.println("cluster-id: " + mp.getClusterId());
		System.out.println("content-type: " + mp.getContentType());
		System.out.println("content-encoding: " + mp.getContentEncoding());
		System.out.println("content-length: " + mp.getContentLength());
		/* 生产者set设置不生效，不知道原因 */
		System.out.println("delivery-mode: " + mp.getDeliveryMode());
		/* 从接收端接收消息开始计数，起始为1 */
		System.out.println("delivery-tag: " + mp.getDeliveryTag());
		System.out.println("priority: " + mp.getPriority());
		System.out.println("consumer-queue: " + mp.getConsumerQueue());
		System.out.println("consumer-tag: " + mp.getConsumerTag());
		System.out.println("correlation-id: " + mp.getCorrelationId());
		System.out.println("received-exchange: " + mp.getReceivedExchange());
		System.out.println("received-routingKey: " + mp.getReceivedRoutingKey());
		/* 生产者set设置不生效，不知道原因 */
		System.out.println("received-userId: " + mp.getReceivedUserId());
		System.out.println("reply-to: " + mp.getReplyTo());
		/* 生产者set设置不生效，不知道原因 */
		System.out.println("delay: " + mp.getDelay());

		System.out.println("========================分割线========================");
		System.out.println("headers: " + headers);
		for(Map.Entry<String, Object> e : headers.entrySet()) {
			System.out.println("key: [" + e.getKey() + "]   ---   value:[" + e.getValue() + "]");
		}
		System.out.println("timestamp: " + timestamp.toString());
		System.out.println("amqp_receivedExchange: " + amqp_receivedExchange);

		try{
			System.out.println("========================分割线========================");
			System.out.println("MESSAGE: " + message);
			System.out.println("MESSAGE BODY: " + new String(message.getBody()));
			System.out.println("MESSAGE PROPERTIES: " + message.getMessageProperties());
			System.out.println("========================分割线========================");
			System.out.println("BODY: " + body);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * queues  指定从哪个队列（queue）订阅消息
	 * @param message
	 * @param channel
	 */
//	@RabbitListener(queues = {QUEUE_NAME1})         // 简略写法
//	@RabbitListener(bindings = @QueueBinding(       // 详细指定
//						exchange = @Exchange(value = "first_exchange",durable = "true",type = "direct"),
//						value = @Queue(value = "first-queue",durable = "true"),
//						key = "queue_one_key1"),
//					exclusive = true)
	public void handleMessage(Message message, Channel channel) throws IOException {
		try {
			// 处理消息
			System.out.println("Consumer Message:" + message);

			// 执行减库存操作
//			storeService.update(new Gson().fromJson(new String(message.getBody()),Order.class));

			/**
			 * 第一个参数 deliveryTag：就是接受的消息的deliveryTag,可以通过msg.getMessageProperties().getDeliveryTag()获得
			 * 第二个参数 multiple：如果为true，确认之前接受到的消息；如果为false，只确认当前消息。
			 * 如果为true就表示连续取得多条消息才发会确认，和计算机网络的中tcp协议接受分组的累积确认十分相似，
			 * 能够提高效率。
			 *
			 * 同样的，如果要nack或者拒绝消息（reject）的时候，
			 * 也是调用channel里面的basicXXX方法就可以了（要指定tagId）。
			 *
			 * 注意：如果抛异常或nack（并且requeue为true），消息会重新入队列，
			 * 并且会造成消费者不断从队列中读取同一条消息的假象。
			 */
			// 确认消息
			// 如果 channel.basicAck   channel.basicNack  channel.basicReject 这三个方法都不执行，消息也会被确认 【这个其实并没有在官方看到，不过自己测试的确是这样哈】
			// 所以，正常情况下一般不需要执行 channel.basicAck
			 channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

			/*
			* 消息的标识，false只确认当前一个消息收到，true确认consumer获得的所有消息
			* channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			*
			* ack返回false，并重新回到队列
			* channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			*
			* 拒绝消息
			* channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
			*
			*/
		}catch (Exception e){
			System.out.println("Consumer  handleMessage " + message + "error: " + e);
			// 处理消息失败，将消息重新放回队列
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
		}
	}
}

