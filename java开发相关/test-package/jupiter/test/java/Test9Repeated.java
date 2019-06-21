package com.simple.jupiter;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @ClassName: Test9Repeated
 * @Description: 重复测试
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 下午11:04
 * @Version: 1.0
 **/
public class Test9Repeated {

    @BeforeEach
    void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        int current = repetitionInfo.getCurrentRepetition();
        int total = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();
        System.out.println(String.format("准备执行函数%s, %d of %d", methodName, current, total));
    }

    @RepeatedTest(3)
    void simpleRepeat() {}

    @RepeatedTest(value = 4, name = "显示原名: {displayName} 共执行{totalRepetitions}次，当前第{currentRepetition}次")
    void namedRepeat(RepetitionInfo info) {
        System.out.println("总次数: " + info.getTotalRepetitions() + "，当前次数: " + info.getCurrentRepetition());
    }

    @RepeatedTest(value = 1, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("详情...")
    void customDisplayWithLongPattern(TestInfo testInfo) {
        assertEquals("详情... :: repetition 1 of 1", testInfo.getDisplayName());
    }
}
