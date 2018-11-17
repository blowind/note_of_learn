package com.zxf.reflection.jdk.model.impl;

import com.zxf.reflection.jdk.model.HelloWorld;

/**
 * @ClassName: HelloWorldImpl
 * @Description: 接口实现
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:24
 * @Version: 1.0
 **/
public class HelloWorldImpl implements HelloWorld {
    @Override
    public void sayHelloWorld() {
        System.out.println("Hello World");
    }
}
