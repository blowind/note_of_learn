package com.zxf.aspectj;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description cflow控制流
 * @date 2018/06/26 13:47
 */
public class Service6B {
	public static void methodB() {
		Service6C.methodC();
		int a=2, b=4;
		System.out.println("a + b = " + (a + b));
	}
}
