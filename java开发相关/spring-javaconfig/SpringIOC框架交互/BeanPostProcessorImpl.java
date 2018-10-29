package com.zxf.myspring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 本接口对所有的Bean生效，即每个Bean的生成过程中都会使用此处实现的两个方法
 * @date 2018/10/29 09:32
 */
@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("类【" + bean.getClass().getSimpleName() + "】的对象 " + beanName + " 开始实例化");
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("类【" + bean.getClass().getSimpleName() + "】的对象" + beanName + " 实例化完成");
		return bean;
	}
}
