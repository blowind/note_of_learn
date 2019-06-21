package com.simple.jupiter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @ClassName: Test4Disabled
 * @Description: 注解不执行的测试用例
 *               可另外参考Test3Assumptions查看如何根据前一个断言的结果确定是否执行后一个结果
 *                    参考Test5Conditional看各种便捷的外在条件判断
 *
 *              @Disabled 可用于注解类
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 下午2:51
 * @Version: 1.0
 **/
public class Test4Disabled {

    @Disabled
    @Test
    void testWillBeSkipped() {
        System.out.println("skipped test");
    }

    @Disabled("本方法因为某些原因不执行")
    @Test
    void disableWithReason() {
        System.out.println("skipped test 2");
    }

    @Test
    void testWillBeExecuted() {
        System.out.println("running one");
    }
}
