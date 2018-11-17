package com.zxf.reflection.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName: JdkProxy
 * @Description: 基本的拦截操作示例，此处没有提取共性操作并做取舍，仅仅是函数调用前后添加基本操作
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:26
 * @Version: 1.0
 **/
public class JdkProxy implements InvocationHandler {
    /*存放真实对象，在通过代理对象调用真实对象的方法时(invoke()函数中)需要使用*/
    private Object target = null;

    /*绑定代理对象和真实对象的代理关系，返回代理对象*/
    public static Object bind(Object target) {
        JdkProxy freshOne = new JdkProxy();
        // 保存真实对象target到代理对象里
        freshOne.target = target;
		/*建立并生成代理对象
		  参数1:指定target本身的类加载器作为类加载器
		  参数2:指定动态代理对象挂载的接口，此处为target实现的接口
		  参数3:定义实现方法逻辑的代理类，此处为当前对象，该对象必须实现InvocationHandler接口*/
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), freshOne);
    }

    @Override
    /*实现代理逻辑方法
     * 参数1:代理对象，即前述bind方法生成的对象
     * 参数2:当前调度的方法
     * 参数3:调度方法的参数*/
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入代理逻辑方法");
        System.out.println("在调度真实对象之前的服务");
        Object obj = method.invoke(target, args);
        System.out.println("在调度真实对象之后的服务");
        return obj;
    }
}
