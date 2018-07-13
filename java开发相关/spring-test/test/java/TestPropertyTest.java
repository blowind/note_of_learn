package com.springtest;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 测试类专用属性引入方法
 * @date 2018/07/13 19:15
 */
//TestPropertySource引入的属性具有最高优先级
@ContextConfiguration
//@TestPropertySource("classpath:/test.properties")
@TestPropertySource(locations = "classpath:/test.properties", properties = {"timezone = GMT", "port: 4242"})
public class TestPropertyTest {

}
