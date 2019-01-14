package com.zxf.myspring.demo;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 09:49
 */
//@Component
public class SimpleBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean,
		ResourceLoaderAware {

	private ResourceLoader loader;

	/*Configuration设置后，在Bean初始化时调用*/
	public void init() {
		System.out.println("类【" + this.getClass().getSimpleName() + "】的对象执行@Bean注解中init属性指定的方法");
	}

    /*Configuration设置后，在Bean销毁时调用*/
    public void destroy() {
        System.out.println("类【" + this.getClass().getSimpleName() + "】的对象执行@Bean注解中destroy属性指定的方法");
    }

    /*本质上下面两个注解等价于Configuration中Bean的initMethod设置和destroyMethod设置*/
    @PostConstruct
    public void init2() {
        System.out.println("类【" + this.getClass().getSimpleName() + "】的对象执行本对象中@PostConstruct注解的方法");
    }

    @PreDestroy
    public void destroy2() {
        System.out.println("类【" + this.getClass().getSimpleName() + "】的对象执行本对象中@PreDestroy注解的方法");
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

	/* 这个接口方法是给spring容器去填入loader对象  */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.loader = resourceLoader;
	}

	public void outputResult() {
		Resource resource = loader.getResource("classpath:test.txt");
		try{
			System.out.println("ResourceLoader加载的文件内容为: " + IOUtils
					.toString(resource.getInputStream(), "UTF-8"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
