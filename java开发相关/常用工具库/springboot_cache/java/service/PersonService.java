package com.zxf.springcache.service;

import com.zxf.springcache.model.Person;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description Person对象对外服务接口
 * @date 2018/06/02 11:52
 */
public interface PersonService {
	Person save(Person person);

	void remove(Long id);

	Person findOne(Person person);
}
