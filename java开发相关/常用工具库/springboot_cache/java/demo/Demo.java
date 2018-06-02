package com.zxf.springcache.demo;

import com.zxf.springcache.model.Person;
import com.zxf.springcache.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 执行成果验证动作的类
 * @date 2018/06/02 14:20
 */
@Component
public class Demo implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	PersonService personService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)  {
		System.out.println("=======================start=======================");
		Person a = new Person("a", 1, "aaa");
		Person b = new Person("b", 2, "bbb");
		Person c = new Person("c", 3, "ccc");
		Person retA = personService.save(a);
		Person retB = personService.save(b);

		personService.remove(retA.getId());
		Person retC = personService.save(c);

		System.out.println(personService.findOne(retB));
		System.out.println(personService.findOne(retA));
		System.out.println(personService.findOne(retC));

		while(true) {
			try{
				Thread.sleep(3000);
				System.out.println(personService.findOne(retC));
			}catch (Exception ep) {
				ep.printStackTrace();
			}

		}
	}

}
