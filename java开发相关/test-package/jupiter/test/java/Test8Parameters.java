package com.simple.jupiter;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @ClassName: Test8Parameters
 * @Description: 给测试类构造函数或者测试方法传入参数
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 下午9:43
 * @Version: 1.0
 **/

@DisplayName("展示TestInfo使用方法")
public class Test8Parameters {


    /**
     * 展示在构造函数中通过testInfo获取类的显示名
     */
    Test8Parameters(TestInfo testInfo) {
        assertEquals("展示TestInfo使用方法", testInfo.getDisplayName());
        System.out.println(testInfo.getDisplayName());
        System.out.println(testInfo.getTestClass());
    }

    /**
     * 展示在每个测试函数执行前通过testInfo获取显示名
     */
    @BeforeEach
    void init(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
//        assertTrue(displayName.equals("TEST 1") || displayName.equals("test2()"));
    }

    /**
     * 展示通过testInfo获取显示名 和 tag
     */
    @Test
    @DisplayName("TEST 1")
    @Tag("my-tag")
    void test1(TestInfo testInfo) {
        assertEquals("TEST 1", testInfo.getDisplayName());
        assertTrue(testInfo.getTags().contains("my-tag"));
    }

    @Test
    void test2() {}

    /**
     * 通过repetitionInfo获取循环执行的总次数和当前次数信息
     * 仅在@RepeatedTest、@BeforeEach、@AfterEach注解中可以获取该类型参数
     */
    @RepeatedTest(10)
    void repeat(RepetitionInfo repetitionInfo) {
        System.out.println("循环总次数: " + repetitionInfo.getTotalRepetitions()
                + ", 当前次数: " + repetitionInfo.getCurrentRepetition());
    }


    /**
     * 通过TestReporter输出测试结果到文档，
     * 一般是输出到TestExecutionListener的reportingEntryPublished()方法
     * 用于通过IDE定制输出
     */
    @Test
    void reportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("状态信息");
    }

    @Test
    void reportKeyValuePair(TestReporter testReporter) {
        testReporter.publishEntry("key", "value");
    }

    @Test
    void reportMultipleKeyValuePairs(TestReporter testReporter) {
        Map<String, String> values = new HashMap<>();
        values.put("key1", "value1111");
        values.put("Key2", "value2222");
        testReporter.publishEntry(values);
    }


}
