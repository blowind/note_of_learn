package com.zxf.aspectj;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description cflow控制流
 * @date 2018/06/26 13:46
 */
public class Service6A {
	public static void methodA() {
		Service6B.methodB();
	}
}
