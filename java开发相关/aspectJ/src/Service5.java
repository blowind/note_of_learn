package com.zxf.aspectj;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description within作用域限制
 * @date 2018/06/26 11:35
 */
public class Service5 {
	protected static String name = "Robert Lighthizer";
	private String firstname = "Robert";
	private String lastname = "Lighthizer";

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void test(int a, float b) {
		System.out.println("a + b = " + (a + b));
	}
}
