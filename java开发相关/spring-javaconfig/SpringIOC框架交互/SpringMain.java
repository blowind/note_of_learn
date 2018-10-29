package com.zxf.myspring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 09:29
 *
 * 执行结果：
 * 类【EventListenerMethodProcessor】的对象 org.springframework.context.event.internalEventListenerProcessor 开始实例化
 * 类【EventListenerMethodProcessor】的对象org.springframework.context.event.internalEventListenerProcessor 实例化完成
 * 类【DefaultEventListenerFactory】的对象 org.springframework.context.event.internalEventListenerFactory 开始实例化
 * 类【DefaultEventListenerFactory】的对象org.springframework.context.event.internalEventListenerFactory 实例化完成
 * 类【BeanConfig$$EnhancerBySpringCGLIB$$54741c24】的对象 beanConfig 开始实例化
 * 类【BeanConfig$$EnhancerBySpringCGLIB$$54741c24】的对象beanConfig 实例化完成
 * 类【DisposableBeanImpl】的对象 disposableBeanImpl 开始实例化
 * 类【DisposableBeanImpl】的对象disposableBeanImpl 实例化完成
 * 类【SimpleBean】的构造函数执行
 * 类【SimpleBean】调用BeanNameAware接口的setBeanName()方法
 * 类【SimpleBean】调用BeanFactoryAware接口的setBeanFactory()方法
 * 类【SimpleBean】调用ApplicationContextAware接口的setApplicationContext()方法
 * 类【SimpleBean】的对象 getSimpleBean 开始实例化
 * 类【SimpleBean】的对象执行自定义初始化方法init2()
 * 类【SimpleBean】调用InitializingBean接口的afterPropertiesSet()方法
 * 类【SimpleBean】的对象执行自定义初始化方法init()
 * 类【SimpleBean】的对象getSimpleBean 实例化完成
 * ############# before context.start ##############
 * ############# after context.start ##############
 * ############# before context.close ##############
 * 类【SimpleBean】的对象执行自定义销毁方法destroy2()
 * 类【SimpleBean】的对象执行自定义销毁方法destroy()
 * 【Spring IOC框架】调用接口DisposableBean的destroy方法
 * ############# after context.close ##############
 *
 */
public class SpringMain {

	public static void main(String[] argv) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.zxf.myspring");
		System.out.println("############# before context.start ##############");
		context.start();
		System.out.println("############# after context.start ##############");
		/* 不执行下面这条语句时，Bean的destroy方法和DisposableBean接口的destroy方法可能不会执行 */
		System.out.println("############# before context.close ##############");
		context.close();
		System.out.println("############# after context.close ##############");
	}
}
