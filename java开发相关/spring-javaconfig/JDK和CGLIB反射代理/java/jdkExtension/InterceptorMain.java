package com.zxf.reflection.jdkExtension;

import com.zxf.reflection.jdk.model.HelloWorld;
import com.zxf.reflection.jdk.model.impl.HelloWorldImpl;
import com.zxf.reflection.jdkExtension.interceptor.impl.MyInterceptor;
import com.zxf.reflection.jdkExtension.proxy.InterceptorJdkProxy;

/**
 * @ClassName: InterceptorMain
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:31
 * @Version: 1.0
 **/
public class InterceptorMain {
    public static void main(String[] args) {
        System.out.println("==============通过类全限定名路径获取拦截器=============");
        HelloWorld proxy1 = (HelloWorld) InterceptorJdkProxy.bind(new HelloWorldImpl(), "com.zxf.reflection.jdkExtension.interceptor.impl.MyInterceptor");
        proxy1.sayHelloWorld();
        System.out.println("==============通过类名获取拦截器=============");
        HelloWorld proxy2 = (HelloWorld) InterceptorJdkProxy.bind(new HelloWorldImpl(), MyInterceptor.class);
        proxy2.sayHelloWorld();
        System.out.println("==============不涉及拦截器，直接调用对象方法=============");
        HelloWorld proxy3 = (HelloWorld) InterceptorJdkProxy.bind(new HelloWorldImpl(), null);
        proxy3.sayHelloWorld();
    }
}
