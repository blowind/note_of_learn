package com.zxf.myspring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 09:29
 *
 * 执行结果：
 * 类【EventListenerMethodProcessor】的对象org.springframework.context.event.internalEventListenerProcessor调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【EventListenerMethodProcessor】的对象org.springframework.context.event.internalEventListenerProcessor调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * 类【DefaultEventListenerFactory】的对象org.springframework.context.event.internalEventListenerFactory调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【DefaultEventListenerFactory】的对象org.springframework.context.event.internalEventListenerFactory调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * 类【BeanConfig$$EnhancerBySpringCGLIB$$e4a8e56c】的对象beanConfig调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【BeanConfig$$EnhancerBySpringCGLIB$$e4a8e56c】的对象beanConfig调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * 类【DisposableBeanImpl】的对象disposableBeanImpl调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【DisposableBeanImpl】的对象disposableBeanImpl调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * 类【SimpleBean】的构造函数执行
 * 类【SimpleBean】调用BeanNameAware接口的setBeanName()方法
 * 类【SimpleBean】调用BeanFactoryAware接口的setBeanFactory()方法
 * 类【SimpleBean】调用ApplicationContextAware接口的setApplicationContext()方法
 * 类【SimpleBean】的对象getSimpleBean调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【SimpleBean】的对象执行本对象中@PostConstruct注解的方法
 * 类【SimpleBean】调用InitializingBean接口的afterPropertiesSet()方法
 * 类【SimpleBean】的对象执行@Bean注解中init属性指定的方法
 * 类【SimpleBean】的对象getSimpleBean调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * ############# before context.start ##############
 * ############# after context.start ##############
 * ############# before context.close ##############
 * 类【SimpleBean】的对象执行本对象中@PreDestroy注解的方法
 * 类【SimpleBean】的对象执行@Bean注解中destroy属性指定的方法
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
