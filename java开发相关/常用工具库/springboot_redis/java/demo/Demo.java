package com.zxf.bootredis.demo;

import com.zxf.bootredis.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 验证代码
 * @date 2018/06/03 17:06
 */
@Component
public class Demo implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private PersonService personService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent e) {
		System.out.println("=================start=====================");
		personService.setCacheObject("person", "p1");

		System.out.println(personService.getCacheObject("person"));
		System.out.println(personService.getCacheObject("counter"));
	}
}
