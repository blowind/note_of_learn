package com.zxf.reflection.cglib;

import com.zxf.reflection.cglib.model.ReflectServiceImpl;
import com.zxf.reflection.cglib.proxy.CglibProxy;

/**
 * @ClassName: CglibMain
 * @Description: 使用CGLIB的反射功能进行代理，优点是被代理的类不必实现一个接口，缺点是代理是通过变成子类来实现
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 13:56
 * @Version: 1.0
 **/
public class CglibMain {
    public static void main(String[] args) {
        CglibProxy cp = new CglibProxy();
        ReflectServiceImpl obj = (ReflectServiceImpl)cp.getProxy(ReflectServiceImpl.class);
        obj.sayHello("张三");
    }
}
