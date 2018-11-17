package com.zxf.reflection.jdk;

import com.zxf.reflection.jdk.model.HelloWorld;
import com.zxf.reflection.jdk.model.impl.HelloWorldImpl;
import com.zxf.reflection.jdk.proxy.JdkProxy;

/**
 * @ClassName: JdkMain
 * @Description: 使用JDK的反射功能进行代理，缺点是被代理的类必须实现一个接口
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:22
 * @Version: 1.0
 **/
public class JdkMain {

    public static void main(String[] args) {
//        因为jdk.bind()函数内部调用的Proxy.newProxyInstance()方法第三个参数是this，所以
//        JdkProxy jdk = ;
        HelloWorld proxy = (HelloWorld) JdkProxy.bind(new HelloWorldImpl());
        proxy.sayHelloWorld();
    }
}
