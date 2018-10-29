package com.zxf.myspring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 09:49
 */
//@Component
public class SimpleBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

	/*Configuration设置后，在Bean初始化时调用*/
	public void init() {
		System.out.println("类【" + this.getClass().getSimpleName() + "】的对象执行自定义初始化方法init()");
	}

	/*Configuration设置后，在Bean销毁时调用*/
	public void destroy() {
		System.out.println("类【" + this.getClass().getSimpleName() + "】的对象执行自定义销毁方法destroy()");
	}

	/*本质上下面两个注解等价于Configuration中Bean的initMethod设置和destroyMethod设置*/
	@PostConstruct
	public void init2() {
		System.out.println("类【" + this.getClass().getSimpleName() + "】的对象执行自定义初始化方法init2()");
	}

	@PreDestroy
	public void destroy2() {
		System.out.println("类【" + this.getClass().getSimpleName() + "】的对象执行自定义销毁方法destroy2()");
	}

	/*正常来说不用显示指定构造函数，Spring会默认生成无参构造函数*/
	public SimpleBean() {
		System.out.println("类【" + this.getClass().getSimpleName() + "】的构造函数执行");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("类【" + this.getClass().getSimpleName() + "】调用BeanNameAware接口的setBeanName()方法");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("类【" + this.getClass().getSimpleName() + "】调用BeanFactoryAware接口的setBeanFactory()方法");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("类【" + this.getClass().getSimpleName() + "】调用ApplicationContextAware接口的setApplicationContext()方法");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("类【" + this.getClass().getSimpleName() + "】调用InitializingBean接口的afterPropertiesSet()方法");
	}
}
