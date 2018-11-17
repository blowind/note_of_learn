package com.zxf.reflection.cglib.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * @ClassName: CglibProxy
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:57
 * @Version: 1.0
 **/
public class CglibProxy implements MethodInterceptor {
    /*生成CGLIB代理对象，注意整个操作过程不涉及代理接口*/
    public Object getProxy(Class cls) {
        /*CGLIB enhaner增强类对象*/
        Enhancer enhancer = new Enhancer();
        /*设置增强类型，本质上就是设置被代理的对象*/
        enhancer.setSuperclass(cls);
		/*定义代理逻辑对象为当前对象，即当前对象为代理人
		  要求当前对象实现MethodInterceptor接口*/
        enhancer.setCallback(this);
        /*返回代理对象*/
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用真实对象前");
        Object result = methodProxy.invokeSuper(proxy, args);
        System.out.println("调用真实对象后");
        return result;
    }
}
