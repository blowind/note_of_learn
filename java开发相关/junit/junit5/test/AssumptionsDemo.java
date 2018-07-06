package com.junit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/07/06 09:20
 */
public class AssumptionsDemo {
	@Test
	void testOnlyOnCiServer() {
		//  假设成立的前提下(即在CI环境上)才会运行后续代码
		assumeTrue("CI".equals(System.getenv("ENV")));

		System.out.println("code after assumeTrue");
	}

	@Test
	void testOnlyOnDeveloperWorkstation() {
		//  在开发环境上才执行后续代码
		assumeTrue("DEV".equals(System.getenv("ENV")),
				() -> "Aborting test: not on developer workstation");
	}

	@Test
	void testInAllEnvironments() {
		// assumingThat仅对内部断言进行遮断，不影响外部断言执行
		assumingThat("CI".equals(System.getenv("ENV")),
				() ->  assertEquals(2, 2) );

		assertEquals("a string", "a string");
	}

	//  实验性质注解，支持javascript，groovy或者其他JSR223定义的表达式
	@Test
	@EnabledIf("2 * 3 == 6")
	void willBeExecuted() {
		System.out.println("根据表达式条件选择执行");
	}

	@RepeatedTest(10)
	@DisabledIf("Math.random() < 100")
	void mightNotBeExecuted() {
		System.out.println("运行10次，根据随机值大小选择性执行");
	}

	@Test
	@DisabledIf("/32/.test(systemProperty.get('os.arch'))")
	void disabledOn32BitArchitecture() {
		assertFalse(System.getProperty("os.arch").contains("32"));
	}

	@Test
	@EnabledIf("'CI' == systemEnvironment.get('ENV')")
	void onlyOnCiServer() {
		assertFalse("CI".equals(equals(System.getenv("ENV"))));
	}
}
