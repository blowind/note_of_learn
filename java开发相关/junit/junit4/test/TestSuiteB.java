package com.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/06/30 14:38
 */
@RunWith(value = Suite.class)
@Suite.SuiteClasses(value = {TestSuiteA.class, ParameterizedTest.class})
public class TestSuiteB {
}
