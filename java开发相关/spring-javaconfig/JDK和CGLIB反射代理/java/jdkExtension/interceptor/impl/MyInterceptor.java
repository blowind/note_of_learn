package com.zxf.reflection.jdkExtension.interceptor.impl;

import com.zxf.reflection.jdkExtension.interceptor.Interceptor;

import java.lang.reflect.Method;

/**
 * @ClassName: MyInterceptor
 * @Description: 拦截器的具体共性动作实现
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:33
 * @Version: 1.0
 **/
public class MyInterceptor implements Interceptor {
    @Override
    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("反射方法前逻辑");
        return false;
    }

    @Override
    public void after(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("反射方法后逻辑");
    }

    @Override
    public void around(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("取代被代理对象的方法");
    }
}
