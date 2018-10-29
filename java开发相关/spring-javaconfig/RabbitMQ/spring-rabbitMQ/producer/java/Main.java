package com.rabbitmq;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description spring运行主程序(非mvc)
 * @date 2018/07/18 20:46
 */
public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.rabbitmq.config");
		context.start();
	}
}
