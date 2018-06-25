package com.zxf.aspectj;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 配合Service1的测试类
 * @date 2018/06/25 11:31
 */
public class Test1 {
	public void runTest() {
		Service1 s1 = new Service1("in Test1");
		s1.test(2, 3.5f);
	}
}
