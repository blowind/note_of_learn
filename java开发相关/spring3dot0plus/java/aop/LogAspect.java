package com.zxf.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect   /*  声明一个切面 */
@Component  /*  将切面变成一个bean  */
public class LogAspect {

    @Pointcut("@annotation(com.zxf.aop.Action)")  /* 声明切点 */
    public void annotationPointCut() {}

    /*  声明一个建言，并使用前述定义的切点 */
    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        /*  通过反射获得注解上的属性，此处可以做相关操作 */
        Action action = method.getAnnotation(Action.class);
        System.out.println("注解式拦截：" + action.name());
    }

    /*  声明一个建言，此建言直接使用拦截规则作为参数 */
    @Before("execution(* com.zxf.aop.DemoMethodService.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则式拦截：" + method.getName());
    }
}
