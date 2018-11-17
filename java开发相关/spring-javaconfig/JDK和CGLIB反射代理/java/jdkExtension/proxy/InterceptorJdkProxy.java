package com.zxf.reflection.jdkExtension.proxy;

import com.zxf.reflection.jdkExtension.interceptor.Interceptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName: InterceptorJdkProxy
 * @Description: 使用自定义拦截器抽取共性操作后，进行相关判断并操作
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:34
 * @Version: 1.0
 **/
public class InterceptorJdkProxy implements InvocationHandler {
    private Object target;
    private String interceptorClassPath = null;
    private Class<? extends Interceptor> interceptorClass = null;

    public InterceptorJdkProxy(Object target) {
        this.target = target;
    }

    public InterceptorJdkProxy(Object target, String interceptorClass) {
        this.target = target;
        this.interceptorClassPath = interceptorClass;
    }

    public InterceptorJdkProxy(Object target, Class<? extends Interceptor> interceptorClass) {
        this.target = target;
        this.interceptorClass = interceptorClass;
    }

    /*绑定定制化拦截器，返回一个代理占位*/
    public static Object bind(Object target, Object interceptorParam) {
        if(interceptorParam instanceof String) {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InterceptorJdkProxy(target, String.valueOf(interceptorParam)));
        }else if(interceptorParam instanceof Class) {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InterceptorJdkProxy(target, (Class)interceptorParam));
        }else{
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InterceptorJdkProxy(target));
        }
    }

    @Override
    /*拦截器的拦截逻辑放在此处实现*/
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(interceptorClass != null || interceptorClassPath != null) {
            Object result = null;
            /*通过反射生成拦截器*/
            Interceptor interceptor = interceptorClass != null ? interceptorClass.newInstance() : (Interceptor)Class.forName(interceptorClassPath).newInstance();
            /*调用前置方法，返回true则反射原有方法，否则执行around拦截方法*/
            if(interceptor.before(proxy, target, method, args)) {
                result = method.invoke(target, args);
            }else{
                interceptor.around(proxy, target, method, args);
            }
            /*统一调用后置方法*/
            interceptor.after(proxy,  target, method, args);
            return result;
        }else{
            /*如果未注册自定义拦截类(设置拦截器)，则直接反射原有方法*/
            return method.invoke(target, args);
        }
    }
}
