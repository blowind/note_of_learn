package com.zxf.zxfspring.config;

import com.zxf.zxfspring.condition.DataSourceCondition;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 14:09
 */
@Configuration
@ComponentScan(basePackages = "com.zxf.zxfspring")    /*指定注解配置路径*/
@Import({AppConfig2.class, AppConfig3.class})  /*引入其他Java配置文件*/
@ImportResource({"classpath:spring-dataSource.xml"})  /*指定xml配置文件*/
@PropertySource(value = {"classpath:spring-dataSource.properties"}, ignoreResourceNotFound = true)
public class AppConfig {

	/* JAVA_OPTS="-Dspring.profiles.active=dev"  配置JVM参数触发Profile的选择*/

	/*使用方法上的注解@Bean注入一个Bean到SpringIOC容器*/
	@Bean(name = "dataSource")
	/*指定这个DataSource用于开发环境*/
	@Profile("dev")
	public DataSource getDataSource() {
		Properties props = new Properties();
		props.setProperty("driver", "com.mysql.jdbc.Driver");
		props.setProperty("url", "jdbc:mysql://localhost:3306/dbcp");
		props.setProperty("username", "root");
		props.setProperty("password", "123456");
		DataSource dataSource = null;
		try{
			dataSource = BasicDataSourceFactory.createDataSource(props);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return dataSource;
	}




	/*
	除了PropertySource引用文件，还得有下面的Bean的辅助，
	才能使用@Value引如properties文件中的值，否则只能通过
	context.getEnvironment().getProperty()获取   */
	@Bean
	public PropertySourcesPlaceholderConfigurer config() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${jdbc.database.driver}")
	private String driver = null;
	@Value("${jdbc.database.url}")
	private String url;
	@Value("${jdbc.database.username}")
	private String username;
	@Value("${jdbc.database.password}")
	private String password;

	@Bean
	public DataSource getDataSource2() {
		Properties props = new Properties();
		props.setProperty("driver", driver);
		props.setProperty("url", url);
		props.setProperty("username", username);
		props.setProperty("password", password);
		DataSource dataSource = null;
		try{
			dataSource = BasicDataSourceFactory.createDataSource(props);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return dataSource;
	}

	/*条件化装备Bean：
	  当且仅当DataSourceCondition实现的接口方法matches返回true时生成bean*/
	@Bean
	@Conditional({DataSourceCondition.class})
	public DataSource getDataSource2(
			@Value("${jdbc.database.driver}") String driver,
			@Value("${jdbc.database.url}") String url,
			@Value("${jdbc.database.username}") String username,
			@Value("${jdbc.database.password}") String password) {
		Properties props = new Properties();
		props.setProperty("driver", driver);
		props.setProperty("url", url);
		props.setProperty("username", username);
		props.setProperty("password", password);
		DataSource dataSource;
		try{
			dataSource = BasicDataSourceFactory.createDataSource(props);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return dataSource;
	}

}
