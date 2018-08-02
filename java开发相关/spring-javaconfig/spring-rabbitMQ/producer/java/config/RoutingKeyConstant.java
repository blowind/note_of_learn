package com.rabbitmq.config;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description routingKey储存类，以免config和send多处配置不便修改
 * @date 2018/07/27 11:13
 */
public class RoutingKeyConstant {

	public static final String DIRECT_ROUTING_KEY_1 = "direct_exchange_routing";

	public static final String DIRECT_ROUTING_KEY_2 = "direct_exchange_routing2";

	public static final String TOPIC_ROUTING_KEY_1 = "topic_exchange_routing";
}
