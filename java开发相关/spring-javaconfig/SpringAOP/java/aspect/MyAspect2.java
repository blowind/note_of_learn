package com.zxf.aop.aspect;

import com.zxf.aop.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyAspect2
 * @Description: 多切面展示，使用@Order注解指定多切面执行顺序，否则顺序随机
 * @Author: ZhangXuefeng
 * @Date: 2018/11/18 11:57
 * @Version: 1.0
 **/
@Component
@Aspect
@Order(1)
public class MyAspect2 {
    /*定义切点，标注在方法pointCut()上，后续用方法名来引用切点*/
    @Pointcut("execution(* com.zxf.aop.service.impl.UserServiceImpl.printUser(..))")
    public void pointCut() {}

    @Before("pointCut() && args(user)")
    public void before(JoinPoint jp, User user) {
        Object[] args = jp.getArgs();
        System.out.println("before增强版 ......");
        System.out.println("user参数: " + user);
        System.out.println("args参数: " + args[0]);
    }
}
