package com.zxf.spring.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 *  @ClassName: WebFluxMain
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/15 16:17
 *  @Version: 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.zxf.spring.webflux")
/*因为引入了JPA，默认情况下需要另外配置数据源
通过@EnableAutoConfiguration排除原有自动配置的数据源*/
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
/*webflux下，驱动MongoDB的JPA接口*/
@EnableReactiveMongoRepositories(basePackages = "com.zxf.spring.webflux.repository")
public class WebFluxMain {
	public static void main(String[] args) {
		SpringApplication.run(WebFluxMain.class, args);
	}
}
