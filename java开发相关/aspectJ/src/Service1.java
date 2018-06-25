package com.zxf.aspectj;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 测试服务类
 * @date 2018/06/25 09:42
 */
public class Service1 {
	private String name;

	public Service1(String name) {
		this.name = name;
	}

	public void test(int a, float b) {
		System.out.println("a+b=" + (a+b));
	}

	@Override
	public String toString() {
		return "Service{" + "name='" + name + '\'' + '}';
	}
}
