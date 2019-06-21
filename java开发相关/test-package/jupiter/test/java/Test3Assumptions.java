package com.simple.jupiter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * @ClassName: Test3Assumptions
 * @Description: 环境/前置条件判断功能，本质上不如condition包下的外部条件判断好用
 *               只有在前置条件假设成立的情况下才运行后面的测试
 *               所有的假设api都在org.junit.jupiter.api.Assumptions包下
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 下午2:34
 * @Version: 1.0
 **/
public class Test3Assumptions {

    /**
     * 运行环境判断：是否是CI服务器
     */
    @Test
    void testOnlyOnCiServer() {
        //  在CI环境上才会运行后续代码
        assumeTrue("CI".equals(System.getenv("ENV")));

        System.out.println("code after assumeTrue");
    }

    /**
     * 运行环境判断：是否是DEV服务器
     */
    @Test
    void testOnlyOnDevServer() {
        //  在DEV环境上才执行后续代码
        assumeTrue("DEV".equals(System.getenv("ENV")),
                () -> "Aborting test: not on developer workstation");
    }

    /**
     * 对一部分断言进行运行环境判断，
     * 不符合前置条件的不执行，
     * 不需要判断前置条件的正常执行
     */
    @Test
    void testInAllEnvironments() {
        // assumingThat仅对内部断言进行遮断，不影响外部断言执行
        assumingThat("CI".equals(System.getenv("ENV")),
                     () ->  assertEquals(3, 2) );

        assertEquals("a string", "a string");
    }

}
