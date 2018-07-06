package com.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/07/06 10:29
 */
// 每个测试类只初始化一次实例，
// 默认是TestInstance.Lifecycle.PER_METHOD，即每个方法创建一个类实例
// 声明此种测试类声明周期后，@BeforeAll和@AfterAll注解的方法不再强制为static，甚至可以是default接口方法
// 通过编译选项设置所有测试类实例的生命周期：
// -Djunit.jupiter.testinstance.lifecycle.default=per_class
// 通过配置文件设置测视类实例生命周期：
// CLASSPATH目录下junit-platform.properties文件添加junit.jupiter.testinstance.lifecycle.default = per_class配置
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConditionalDemo {
	@Test
	@EnabledOnOs(OS.WINDOWS)
	void onlyOnWindowsOs() {
		System.out.println("running on windows");
	}

	@TestOnWindows
	void allInOneTag() {
		System.out.println("all in one tag");
	}

	@Test
	@DisabledOnOs({OS.LINUX, OS.MAC})
	void onOsExceptLinuxOrMac() {
		System.out.println("running on os except linux or mac");
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Test
	@EnabledOnOs(OS.WINDOWS)
	@interface TestOnWindows {}

	@Test
	@EnabledOnJre(JRE.JAVA_8)
	void onlyOnJava8() {
		System.out.println("run when jre is 1.8");
	}

	@Test
	@DisabledOnJre({JRE.JAVA_9, JRE.JAVA_10})
	void exceptJava9AndJava10() {
		System.out.println("run below jre 1.9");
	}

	@Test
	@EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
	void onlyOn64BitArchitectures() {
		System.out.println("on 64-bit system");
	}

	@Test
	@DisabledIfSystemProperty(named = "ci-server", matches = "true")
	void notOnCiServer() {
		System.out.println("not on ci server");
	}
}
