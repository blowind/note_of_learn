package com.zxf.bootredis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxf.bootredis.model.Book;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 《《《《redis的配置config文件，如果全部使用boot的默认配置，可以完全省略本文件》》》》
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
    
    /* 使用properties配置文件后可以省略的JAVA代码配置方式
    config.setMaxIdle(30);   最大空闲数
    config.setMaxTotal(50);   最大连接数
    config.setMaxWaitMillis(2000);  最大等待毫秒数
    */
		return config;
	}

	/** 同一个redis支持不同db下操作的配置方式，因为db选择是在JedisConnectionFactory中设置的，
	 *  所以此处不使用@Bean的单例模式，使用@Scope指定为原型模式，每次调用时都生成新对象
	 * */
	@Scope(scopeName = "prototype")
	@ConfigurationProperties(prefix = "spring.redis")  // 此处未生效，因为是在启动运行一次，在单例模式下有效
	public JedisConnectionFactory getConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setUsePool(true);
		JedisPoolConfig config = getRedisConfig();
		factory.setPoolConfig(config);
		factory.setHostName("47.75.50.16");
		factory.setPassword("1qaz2wsx");
		factory.setTimeout(60000);
		return factory;
	}

	static void printFactoryInfo(JedisConnectionFactory factory, String name) {
		System.out.println("================print " + name + "================");
		System.out.println("database: " + factory.getDatabase());
		System.out.println("host: " + factory.getHostName());
		System.out.println("password: " + factory.getPassword());
		System.out.println("port: " + factory.getPort());
		System.out.println("timeout: " + factory.getTimeout());
		System.out.println("max-total: " + factory.getPoolConfig().getMaxTotal());
		System.out.println("max-idle: " + factory.getPoolConfig().getMaxIdle());
		System.out.println("min-idle: " + factory.getPoolConfig().getMinIdle());
		System.out.println("====================end======================");
	}


	@Bean(name = "templateDB5")
	public RedisTemplate<String, Map<String, String>> redisTemplateDB5() {
		final RedisTemplate<String, Map<String, String>> template = new RedisTemplate<>();
		JedisConnectionFactory factory = getConnectionFactory();
		factory.setDatabase(5);
		printFactoryInfo(factory, "redisTemplateDB5");
		template.setConnectionFactory(factory);
		/* StringRedisSerializer 是 StringRedisTemplate 默认使用的序列化类  */
		StringRedisSerializer serializer = new StringRedisSerializer();
		template.setKeySerializer(serializer);
		template.setHashKeySerializer(serializer);
		template.setHashValueSerializer(serializer);
		return template;
	}

	@Bean(name = "templateDB3")
	public RedisTemplate<String, Map<String, String>> redisTemplateDB3() {
		final RedisTemplate<String, Map<String, String>> template = new RedisTemplate<>();
		JedisConnectionFactory factory = getConnectionFactory();
		factory.setDatabase(3);
		printFactoryInfo(factory, "redisTemplateDB3");
		template.setConnectionFactory(factory);
		StringRedisSerializer serializer = new StringRedisSerializer();
		template.setKeySerializer(serializer);
		template.setHashKeySerializer(serializer);
		template.setHashValueSerializer(serializer);
		return template;
	}

	@Bean(name = "templateJackson")
	public RedisTemplate<String, Book> redisTemplateJackson() {
		final RedisTemplate<String, Book> template = new RedisTemplate<>();
		JedisConnectionFactory factory = getConnectionFactory();
		printFactoryInfo(factory, "redisTemplateJackson");
		template.setConnectionFactory(getConnectionFactory());

		Jackson2JsonRedisSerializer<Book> serializer = new Jackson2JsonRedisSerializer<>(Book.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(om);
		template.setDefaultSerializer(serializer);
		return template;
	}
}
