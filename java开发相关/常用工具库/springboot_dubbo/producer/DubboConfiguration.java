package com.zxf.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfiguration {

	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("provider");
		return applicationConfig;
	}

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
//		registryConfig.setAddress("zookeeper://47.75.50.16:2181");
		registryConfig.setAddress("multicast://224.5.6.7:1234");
//		registryConfig.setClient("curator");
		return registryConfig;
	}
}
