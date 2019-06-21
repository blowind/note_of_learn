package com.simple.jupiter;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @ClassName: Test5Conditional
 * @Description: 根据前置条件判断是否执行
 *               相对于Assumptions的优点是有很多判断系统和外在环境的内置工具类
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 下午2:59
 * @Version: 1.0
 **/

/**
 * TestInstance注解指定每个测试类的生命周期
 * TestInstance.Lifecycle.PER_CLASS   表明每个测试类只初始化一次实例，
 * TestInstance.Lifecycle.PER_METHOD  表明每个方法创建一个类实例，默认值
 * 声明此种测试类声明周期后，@BeforeAll和@AfterAll注解的方法不再强制为static，甚至可以是default接口方法
 * 通过编译选项设置所有测试类实例的生命周期：
 * -Djunit.jupiter.testinstance.lifecycle.default=per_class
 * 通过配置文件设置测视类实例生命周期：
 * CLASSPATH目录下junit-platform.properties文件添加junit.jupiter.testinstance.lifecycle.default = per_class配置
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test5Conditional {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void onlyOnWindowsOs() {
        System.out.println("running on windows");
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

    @TestOnWindows
    void allInOneTag() {
        System.out.println("all in one tag");
    }

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

    /**
     * 根据系统配置参数来控制用例的执行，matches中可以填写正则表达式
     */
    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void onlyOn64BitArchitectures() {
        System.out.println(System.getProperty("os.arch"));
        System.out.println("on 64-bit system");
    }

    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    void notOnCiServer() {
        System.out.println(System.getProperty("ci-server"));
        System.out.println("not on ci server");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "M2_HOME", matches = ".*maven-3.6.1")
    void onlyOnEnvWithMaven() {
        System.out.println(System.getenv());
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "M2_HOME", matches = ".*maven-3.6.1")
    void disableOnEnvWithMaven() {
        System.out.println(System.getenv());
    }

    /**
    * @EnabledIf和@DisabledIf本质上是执行内容脚本做判断
    * 支持javascript，groovy或者其他JSR223定义的表达式
    * 当前都是是实验性质的注解
    * **/

    /**
     * 注解形式的前置条件判断
     *
     */
    @Test
    @EnabledIf("2 * 3 == 6")
    void willBeExecuted() {
        System.out.println("根据表达式条件选择执行");
    }

    /**
     * 注解形式的前置条件判断
     * 根据随机值多次执行
     */
    @RepeatedTest(10)
    @DisabledIf("Math.random() < 100")
    void mightNotBeExecuted() {
        assertEquals(99, 100 - 1);
        System.out.println("运行10次，根据随机值大小选择性执行");
    }

    /**
     * 注解形式的前置条件判断
     * 使用正则表达式判断是否是64位运行环境
     */
    @Test
    @DisabledIf("/32/.test(systemProperty.get('os.arch'))")
    void disabledOn32BitArchitecture() {
        System.out.println(System.getProperty("os.arch"));
        assertFalse(System.getProperty("os.arch").contains("32"));
    }

    @Test
    @EnabledIf("'CI' == systemEnvironment.get('ENV')")
    void onlyOnCiServer() {
        assertFalse("CI".equals(equals(System.getenv("ENV"))));
    }
}
