package com.simple.jupiter;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * MethodOrderer接口有三个自带的实现类：
 * 1、Alphanumeric 按照测试函数名和参数的字母顺序执行
 * 2、OrderAnnotation  按照@Order注解标记的顺序从小到大执行
 * 3、Random 按照随机顺序执行
 *
 * 还可以自定义实现MethodOrderer接口的类用于指定执行顺序
 * */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test6ExecuteOrder {

    @Test
    @Order(1)
    void orderOne() throws Exception {
        System.out.println("Order one");
        Thread.sleep(1000);
    }

    @Test
    @Order(2)
    void orderTwo() throws Exception {
        System.out.println("Order two");
        Thread.sleep(1000);
    }

    @Test
    @Order(100)
    void orderThree() throws Exception {
        System.out.println("Order three");
        Thread.sleep(1000);
    }
}
