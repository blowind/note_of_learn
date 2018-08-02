package com.zxf.bootredis.service;

import com.zxf.bootredis.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description DAO层类
 * @date 2018/06/03 14:24
 */
@Repository
public class PersonService {

	@Autowired
	@Qualifier("templateDB5")
	RedisTemplate<String, Map<String, String>> templateDB5;


	@Autowired
	@Qualifier("templateDB3")
	RedisTemplate<String, Map<String, String>> templateDB3;

	@Autowired
	@Qualifier("templateJackson")
	RedisTemplate<String, Book> templateJackson;

	/* 注入spring boot已经配置好的StringRedisTemplate bean，这个类仅适合string类型的key/value */
	/* 此处发现一个现象，本地生成的新template bean，以config中最后一个bean的factory特征相同，因此此处db是3 */
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/* 注入spring boot已经配置好的RedisTemplate bean */
	@Autowired
	RedisTemplate redisTemplate;


	public void writeToDiffDB() {
		HashOperations<String, String, String> hashOperations = templateDB5.opsForHash();
		Map<String, String> kv = new HashMap<>();
		kv.put("dbindex5", "value555");
		hashOperations.putAll("hsetOP5", kv);

		templateDB3.opsForHash().put("hsetOP3", "dbindex3", "value333");

	}

	/**************        使用Jackson2json Serializer        *********/
	public void setJacksonValue(String key, Book book) {
		ValueOperations<String, Book> operations = templateJackson.opsForValue();
		operations.set(key, book);
		System.out.println("=====使用Jackson2json serialize=======");
		System.out.println(templateJackson.getKeySerializer().getClass().getName());
		System.out.println(templateJackson.getValueSerializer().getClass().getName());
		System.out.println(templateJackson.getDefaultSerializer().getClass().getName());
		System.out.println(templateJackson.getStringSerializer().getClass().getName());
		System.out.println(templateJackson.getHashKeySerializer().getClass().getName());
		System.out.println(templateJackson.getHashValueSerializer().getClass().getName());
	}

	public Book getJacksonValue(String key) {
		ValueOperations<String, Book> operations = templateJackson.opsForValue();
		return operations.get(key);
	}

	/**************        串行化批量操作示例        *********/

	/**	添加zset的pipeline操作  **/
	public void processBatchedCmd() {
		List<Integer> testData = Arrays.asList(1, 2, 3, 4, 5, 6);
		stringRedisTemplate.executePipelined((RedisConnection connection) -> {
			connection.zRemRange("zsetKey".getBytes(), 0, 100);
			for(Integer i : testData) {
				connection.zAdd("zsetKey".getBytes(), i, String.valueOf(i).getBytes());
			}
			return null;
		});
	}


	/**************        基本操作示例区        *********/

	public ValueOperations<String, String> setCacheObject(String key, String value) {
		ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
		operation.set(key, value);
		return operation;

	}

	public Object getCacheObject(String key) {
		ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
		return operation.get(key);
	}

	public <T> ValueOperations<String, T>  setT(String key, T value) {
		ValueOperations<String, T> operations = redisTemplate.opsForValue();
		operations.set(key, value);
		System.out.println("=====default serialize=======");
		System.out.println(redisTemplate.getKeySerializer().getClass().getName());
		System.out.println(redisTemplate.getValueSerializer().getClass().getName());
		System.out.println(redisTemplate.getDefaultSerializer().getClass().getName());
		System.out.println(redisTemplate.getStringSerializer().getClass().getName());
		System.out.println(redisTemplate.getHashKeySerializer().getClass().getName());
		System.out.println(redisTemplate.getHashValueSerializer().getClass().getName());
		return operations;
	}

	public <T> T getT(String key) {
		ValueOperations<String, T> operations = redisTemplate.opsForValue();
		T ret = operations.get(key);
		return ret;
	}

	public <T> ListOperations<String, T> setTList(String key, List<T> dataList) {
		ListOperations listOperations = redisTemplate.opsForList();
		if(dataList != null) {
			for(T elm : dataList) {
				listOperations.rightPush(key, elm);
			}
		}
		return listOperations;
	}

	public <T> List<T> getTList(String key) {
		List<T> dataList = new ArrayList<>();
		ListOperations<String, T> listOperations = redisTemplate.opsForList();
		dataList.addAll(listOperations.range(key, 0, listOperations.size(key)));
		return dataList;
	}

	public <T> BoundSetOperations<String, T> setTSet(String key, Set<T> dataSet) {
		BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
		Iterator<T> it = dataSet.iterator();
		while(it.hasNext()) {
			setOperations.add(it.next());
		}
		return setOperations;
	}

	public <T> Set<T> getTSet(String key) {
		Set<T> dataSet = new HashSet<>();
		BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
		while(setOperations.size() > 0) {
			dataSet.add(setOperations.pop());
		}
		return dataSet;
	}

	public <T> HashOperations<String, String, T> setTMap(String key, Map<String, T> dataMap) {
		HashOperations<String, String ,T> hashOperations = redisTemplate.opsForHash();
		for(Map.Entry<String, T> entry : dataMap.entrySet()) {
			hashOperations.put(key, entry.getKey(), entry.getValue());
		}
		return hashOperations;
	}

	public <T> Map<String, T> getTMap(String key) {
		Map<String, T> map = redisTemplate.opsForHash().entries(key);
		return map;
	}
}
