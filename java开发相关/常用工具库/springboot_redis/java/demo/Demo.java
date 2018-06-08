package com.zxf.bootredis.demo;

import com.zxf.bootredis.model.Book;
import com.zxf.bootredis.model.City;
import com.zxf.bootredis.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		personService.writeToDiffDB();

		System.out.println("=================end stage 1=====================");

		City hangzhou = new City("Hangzhou", "Zhejiang", 310001);
		City nanjing = new City("Nanjing", "Jiangsu", 210000);
		City beijing = new City("Beijing", "Beijing", 100000);

		System.out.println("=================start value=====================");

		personService.setT("value", hangzhou);
		City hz = personService.getT("value");
		System.out.println(hz);

		System.out.println("=================start list=====================");

		personService.setTList("list", Arrays.asList(nanjing, beijing));
		List<City> cityList = personService.getTList("list");
		for(City elm : cityList) {
			System.out.println(elm);
		}

		System.out.println("=================start map=====================");
		Map<String, City> cityMap = new HashMap<>();
		cityMap.put("hz", hangzhou);
		cityMap.put("nj", nanjing);
		cityMap.put("bj", beijing);
		personService.setTMap("map", cityMap);
		Map<String, City> retMap = personService.getTMap("map");
		for(Map.Entry<String, City> entry : retMap.entrySet()) {
			System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
		}

		System.out.println("=================start Jackson2Json=====================");
		Book b1 = new Book("枪炮、病菌与钢铁", 11100000L, 23.56);
		Book b2 = new Book("21世纪资本论", 22200005L, 51.75);
		personService.setJacksonValue("book1", b1);
		personService.setJacksonValue("book2", b2);
		System.out.println("book info:");
		System.out.println(personService.getJacksonValue("book1"));
		System.out.println(personService.getJacksonValue("book2"));
	}
}
