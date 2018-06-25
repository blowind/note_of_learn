package com.zxf.aspectj;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 初始化捕获切点
 * @date 2018/06/25 17:24
 */
public class Service3 extends SuperService3{
	private String name;
	private int age;

	static {
		System.out.println("Static 静态代码块...");
	}

	public Service3(String name) {
		super(name);
		this.name = name;
	}

	public Service3(int age) {
		super("ServiceThree");
		this.age = age;
	}

	public Service3(String name, int age) {
		super(name);
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Service{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}

}
