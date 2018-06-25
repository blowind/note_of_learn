package com.zxf.aspectj;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 测试异常捕获的类
 * @date 2018/06/25 17:16
 */
public class Service2 {
	private String name;

	public Service2(String name) {
		this.name = name;
	}

	public void testException() {
		try{
			throw  new MyException();
		}catch (MyException e) {
			System.out.println("MyException Catch 处理语句...");
		}
	}

	@Override
	public String toString() {
		return "Service{" + "name='" + name + '\'' + '}';
	}
}
