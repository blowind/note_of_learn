package com.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("基本示例")
public class StandardTests {
    // 必须是static
    @BeforeAll
    static void initAll() {
        System.out.println("init all");
    }

    // 不可为static
    @BeforeEach
    void initEach() {
        System.out.println("init each");
    }

    @Test
    @DisplayName("正常示例")
    void testOne() {
        System.out.println("testOne");
    }

    @Test
    @DisplayName("强制失败")
    void failingTest() {
        fail("failing this one");
    }

    @Test
    @Disabled("test diasbled tag")
    @DisplayName("标记跳过")
    void skippedTest() {
        System.out.println("skip this one");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after each");
    }

    // 必须是static
    @AfterAll
    static void  tearDownAll() {
        System.out.println("after all");
    }
}
