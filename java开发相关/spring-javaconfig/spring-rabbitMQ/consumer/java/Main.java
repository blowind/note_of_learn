package com.rabbitmq;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 主程序
 * @date 2018/07/19 10:16
 */
public class Main {

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.rabbitmq.config", "com.rabbitmq.service", "com.rabbitmq.demo");
		context.start();
	}

}
