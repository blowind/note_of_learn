package com.zxf.myspring.config;

import com.zxf.myspring.demo.SimpleBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 10:09
 */
@Configuration
public class BeanConfig {

	/* @Bean注解不能使用在类的标注上，主要用在方法上，可以指定额外的初始化函数和销毁函数 */
	@Bean(initMethod = "init", destroyMethod = "destroy")
	SimpleBean getSimpleBean() {
		return new SimpleBean();
	}
}
