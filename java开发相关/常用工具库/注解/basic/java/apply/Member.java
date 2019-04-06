package com.zxf.apply;

import com.zxf.annotation.*;

/**
 *  @ClassName: Member
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/3/27 14:02
 *  @Version: 1.0
 **/
@DBTable(name = "NEW_MEMBER")
public class Member {
	// 注解中定义了value元素并且该元素时唯一需要赋值的元素时，可以无需使用名值对的写法，只写出值
	@SQLString(30)
	private String firstName;
	@SQLString(value = 50, constraints = @Constraints(allowNull = false))
	private String lastName;
	@SQLInteger
	private Integer age;
	//指定为主键，此处可以看出嵌套注解在使用时并没有很方便，一个变通是将@Constraints提取出来单独使用而不是作为组成上层注解的元素
	@SQLString(value = 30, constraints = @Constraints(primaryKey = true))
	private String handle;

	@Test  // 成功
	public static void m2() {}

	@Test  // 失败，被捕获运行时异常
	public static void m3() {
		throw new RuntimeException("Boom");
	}

	@Test  // 失败，@Test使用异常
	public void m4() {}

	@Test  // 失败，被捕获运行时异常
	public static void m5() {
		throw new RuntimeException("Crash");
	}

	@ExceptionTest(ArithmeticException.class) // 成功，捕获指定异常
	public static void m6() {
		int i = 0;
		i = i / i;
	}

	@ExceptionTest(ArithmeticException.class) // 失败，未捕获指定异常
	public static void m7() {
		int[] a = new int[0];
		int i = a[1];
	}

	@ExceptionTest(ArithmeticException.class)  // 失败，未抛出指定异常
	public static void m8() {}
}
