package com.zxf.aop.aspect;

import com.zxf.aop.validator.UserValidator;
import com.zxf.aop.validator.impl.UserValidatorImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Spring的织入原则：有接口的时候使用JDK反向代理，无接口的时候使用CGLIB反向代理
 *
 * 此处的两个注解都不能少：
 * 通过@Component生成Bean传递给Spring容器，容器才知道如何切面
 * 通过@Aspect注解标明当前是一个切面定义类
 */
@Component
@Aspect
@Order(2)
public class MyAspect {

    /**
     * @DeclareParents注解引入新的类来增强服务
     * value指向要增强功能的目标对象
     * defaultImpl指向用来增强功能的类，
     * 本质上通过给被增强类新增一个实现接口UserValidator并调用实现类处理
     */
    @DeclareParents(value = "com.zxf.aop.service.impl.UserServiceImpl+",
                defaultImpl = UserValidatorImpl.class)
    public UserValidator userValidator;

    /*定义切点，标注在方法pointCut()上，后续用方法名来引用切点*/
    @Pointcut("execution(* com.zxf.aop.service.impl.UserServiceImpl.printUser(..))")
    public void pointCut() {}


    @Before("pointCut()")
    public void before() {
        System.out.println("before ......");
    }


    @After("pointCut()")
    public void after() {
        System.out.println("after ......");
    }

    @AfterReturning("pointCut()")
    public void afterReturning() {
        System.out.println("afterReturning ......");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing() {
        System.out.println("afterThrowing ......");
    }

    /**
     * 建议少用，因为与其他切点配置的位置可能因为版本不同差异较大
     * @param jp
     * @throws Throwable
     */
    @Around("pointCut()")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("around before ......");
        jp.proceed();
        System.out.println("around after ......");
    }
}
