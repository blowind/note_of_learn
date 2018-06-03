package com.zxf.bootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description DAO层类
 * @date 2018/06/03 14:24
 */
@Repository
public class PersonService {
	/* 注入spring boot已经配置好的StringRedisTemplate bean */
	@Autowired
	StringRedisTemplate stringRedisTemplate;


	/* 注入spring boot已经配置好的RedisTemplate bean */
//	@Autowired
//	RedisTemplate redisTemplate;

	public ValueOperations<String, String> setCacheObject(String key, String value) {
		ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
		operation.set(key, value);
		return operation;

	}

	public Object getCacheObject(String key) {
		ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
		return operation.get(key);
	}

}
