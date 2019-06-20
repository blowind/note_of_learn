package com.simple.jupiter;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @ClassName: Test1Base
 * @Description:  基本测试功能展示
 * @Author: ZhangXuefeng
 * @Date: 2019/6/20 23:21
 * @Version: 1.0
 **/

/* @DisplayName 用于展示测试用例名称 */
@DisplayName("基本示例")
public class Test1Base {

    /**
     * @BeforeAll 注解全局初始化函数
     * 在所有方法运行前执行一次，必须注解到static函数上
     */
    @BeforeAll
    static void initAll() {
        System.out.println("init all");
    }

    /**
     * @BeforeEach 注解单个测试方法初始化函数
     * 在每个@Test方法执行前运行一次，不能用于注解static函数上
     */
    @BeforeEach
    void initEach() {
        System.out.println("init each");
    }

    @Test
    @DisplayName("正常示例")
    void testOne() {
        System.out.println("success one");
    }

    @Test
    @DisplayName("强制本方法测试失败")
    void failingTest() {
        fail("failing this one");
    }

    @Test
    @Disabled("test diasbled tag")
    @DisplayName("标记跳过本测试方法")
    void skippedTest() {
        System.out.println("skip this one, has init and after each");
    }

    @Test
    @DisplayName("第二个成功示例，用于验证失败和跳过的示例之后还会继续执行")
    void anotherSuccess() {
        System.out.println("success two");
    }

    /**
     * @AfterEach 注解单个测试方法收尾函数
     * 在每个@Test方法执行后运行一次，不能用于注解static函数上
     */
    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    /**
     * @AfterAll 注解全局收尾函数
     * 在所有方法运行后执行一次，必须注解到static函数上
     */
    @AfterAll
    static void  afterAll() {
        System.out.println("after all");
    }
}
