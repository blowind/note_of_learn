package com.rabbitmq.model;

import lombok.Data;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/07/27 10:49
 */
@Data
public class User {
	private int id;
	private String name;
	private int age;
	private String address;
}
