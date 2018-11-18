package com.zxf.aop.aspect;

import org.aspectj.lang.annotation.*;

//@Aspect
public class MyAspectRaw {

    @Before("execution(* com.zxf.aop.service.impl.UserServiceImpl.printUser(..))")
    public void before() {
        System.out.println("before ......");
    }

    @After("execution(* com.zxf.aop.service.impl.UserServiceImpl.printUser(..))")
    public void after() {
        System.out.println("after ......");
    }

    @AfterReturning("execution(* com.zxf.aop.service.impl.UserServiceImpl.printUser(..))")
    public void afterReturning() {
        System.out.println("afterReturning ......");
    }

    @AfterThrowing("execution(* com.zxf.aop.service.impl.UserServiceImpl.printUser(..))")
    public void afterThrowing() {
        System.out.println("afterThrowing ......");
    }
}
