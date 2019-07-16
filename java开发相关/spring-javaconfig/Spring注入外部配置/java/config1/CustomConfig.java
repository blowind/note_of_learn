package com.zxf.spring.config1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: CustomConfig
 * @Description:  使用配置类对象的实现方式一，通过以下三个注解组合实现
 *                1、@EnableConfigurationProperties 指定配置类并将其作为bean加载到Spring上下文
 *                2、@PropertySource 在SpringMain上指定配置文件位置
 *                2、@ConfigurationProperties 指定配置公共前缀
 * @Author: zhangxuefeng
 * @Date: 2019/7/12 下午3:11
 * @Version: 1.0
 **/
@Configuration
@EnableConfigurationProperties(CustomProperties.class)   // 让spring boot使能 @ConfigurationProperties
@ConditionalOnProperty(name = "my.custom.name", havingValue = "xiaoming")
@Slf4j
public class CustomConfig {

}
