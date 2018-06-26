package com.zxf.aspectj;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 属性设置与获取相关的捕获服务
 * @date 2018/06/26 10:33
 */
public class Service4 {

	// 对name静态属性的访问是直接通过属性名字访问，能被捕获
	// 对name静态属性的设置是有两次，一次是【类】初始化时，一次是通过代码中对变量直接赋值
	protected static String name = "john bolton";

	// 对firstname和lastname属性的访问是通过其getter方法，能被捕获
	// 对firstname和lastname属性的设置有两次，一次是【对象】初始化时，一次是通过set对变量直接赋值
	private String firstname = "john";
	private String lastname = "bolton";

	// 此处是否添加static标记不影响结果(即常量不能被get捕获)，
	// 不过项目需要重新rebuild，怀疑是aspectj的编译器对轻微代码变更不敏感而没有重新编译
	public static final String nickname = "idiot";

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
}
