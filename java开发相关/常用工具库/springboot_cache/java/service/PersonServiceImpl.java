package com.zxf.springcache.service;

import com.zxf.springcache.model.Person;
import com.zxf.springcache.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description Person服务接口实现
 * @date 2018/06/02 11:53
 */
@Service
public class PersonServiceImpl implements PersonService{
	@Autowired
	PersonRepository personRepository;

	/*  无论怎样，都将方法返回值放入缓存，使用value指定缓存的名字(与配置文件中spring.cache.cache-names一致)，key指定map中的key  */
	@Override
	@CachePut(value = "people", key="#person.id")
	public Person save(Person person) {
		Person p = personRepository.save(person);
		System.out.println("为id,key为:" + p.getId() + " 的数据做了缓存");
		return p;
	}

	/*  从缓存中删除一条或者多条key为id的数据，此处实现仅可能删除一条 */
	@Override
	@CacheEvict(value = "people")
	public void remove(Long id){
		System.out.println("删除了id,key为:" + id + "的数据缓存");
		personRepository.deleteById(id);
	}

	/*  在方法执行前Spring先查看缓存中是否有数据，若有则直接返回，若无则调用方法并将方法返回值放入缓存 */
	@Override
	@Cacheable(value = "people", key="#person.id")
	public Person findOne(Person person) {
		Optional<Person> op = personRepository.findById(person.getId());
		op.ifPresent(System.out::println);
		return op.orElse(null);
	}
}
