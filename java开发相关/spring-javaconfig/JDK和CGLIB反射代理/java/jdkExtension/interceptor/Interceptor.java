package com.zxf.reflection.jdkExtension.interceptor;

import java.lang.reflect.Method;

/**
 * @InterfaceName: Interceptor
 * @Description: 拦截器接口，提炼了反射调用的操作
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:32
 * @Version: 1.0
 **/
public interface Interceptor {
    boolean before(Object proxy, Object target, Method method, Object[] args);
    void around(Object proxy, Object target, Method method, Object[] args);
    void after(Object proxy, Object target, Method method, Object[] args);
}
