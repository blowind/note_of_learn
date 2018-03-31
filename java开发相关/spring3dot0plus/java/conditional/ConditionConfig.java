package com.zxf.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionConfig {
	/* 因为是根据条件生成bean，因此一定不要在每个业务类上注解Service之类的，所有bean注解都放在config类里  */
	
	@Bean
	@Conditional(WindowsCondition.class)  /* 符合windows条件则实例化windowsListService */
	public ListService windowsListService() {
		return new WindowsListService();
	}

	@Bean
	@Conditional(LinuxCondition.class)
	public ListService linuxListService() {
		return new LinuxListService();
	}
}
