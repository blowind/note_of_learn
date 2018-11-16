package com.zxf.myspring.framework;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 仅在Spring IOC容器关闭的时候调用一次，本设计针对SpringIOC容器
 * @date 2018/10/29 09:48
 */
@Component
public class DisposableBeanImpl implements DisposableBean {
	@Override
	public void destroy() throws Exception {
		System.out.println("【Spring IOC框架】调用接口DisposableBean的destroy方法");
	}
}
