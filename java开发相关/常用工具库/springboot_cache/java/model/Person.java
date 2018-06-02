package com.zxf.springcache.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 领域模型
 * @date 2018/06/02 11:43
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable{ // 使用ehcache缓存的对象要实现Serializable接口

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private Integer age;
	private String address;

	public Person(String name, Integer age, String address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}

}
