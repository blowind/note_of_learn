package com.zxf.config;

import com.zxf.service.FunctionService;
import com.zxf.service.UseFunctionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan("com.zxf.service")
public class DiConfig {
	
	/*  使用@Bean注解声明当前方法返回值是一个bean，bean的名称是方法名 */
    @Bean
    public FunctionService functionService() {
        return new FunctionService();
    }

	/*  另一种注解方式，直接将FunctionService作为参数给useFunctionService()，
		只要容器中存在某个Bean，就可以在另一个Bean的声明方法的参数中写入  */
    @Bean
    public UseFunctionService useFunctionService(FunctionService functionService) {
        UseFunctionService useFunctionService = new UseFunctionService();
        useFunctionService.setFunctionService(functionService);
        return useFunctionService;
    }
}
