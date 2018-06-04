package com.zxf.bootredis.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description redis的配置config文件，如果全部使用boot的默认配置，可以完全省略本文件
 * @date 2018/06/04 20:39
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

	/**
	 * @Bean 和 @ConfigurationProperties
	 * 该功能在官方文档是没有提到的，我们可以把@ConfigurationProperties和@Bean和在一起使用。
	 * 举个例子，我们需要用@Bean配置一个Config对象，Config对象有a，b，c成员变量需要配置，
	 * 那么我们只要在yml或properties中定义了a=1,b=2,c=3，
	 * 然后通过@ConfigurationProperties就能把值注入进Config对象中
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.redis.jedis.pool")
	public JedisPoolConfig getRedisConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		return config;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	public JedisConnectionFactory getConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setUsePool(true);
		JedisPoolConfig config = getRedisConfig();
		factory.setPoolConfig(config);
		factory.setHostName("47.75.50.16");
		return factory;
	}

	@Bean
	public RedisTemplate<?, ?> getRedisTemplate() {
		JedisConnectionFactory factory = getConnectionFactory();
		System.out.println("database: " + factory.getDatabase());
		System.out.println("host: " + factory.getHostName());
		System.out.println("password: " + factory.getPassword());
		System.out.println("max-idle: " + factory.getPoolConfig().getMaxIdle());
		RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
		return template;
	}

	@Bean
	public RedisTemplate<String, Map<String, String>> redisTemplateOne() {
		final RedisTemplate<String, Map<String, String>> template = new RedisTemplate<>();
		JedisConnectionFactory factory = getConnectionFactory();
		factory.setDatabase(5);
		template.setConnectionFactory(factory);
		StringRedisSerializer serializer = new StringRedisSerializer();
		template.setKeySerializer(serializer);
		template.setHashKeySerializer(serializer);
		template.setHashValueSerializer(serializer);
		return template;
	}

}
