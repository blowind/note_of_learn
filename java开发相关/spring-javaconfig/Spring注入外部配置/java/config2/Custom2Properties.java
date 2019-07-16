package com.zxf.spring.config2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Custom2Properties
 * @Description:  使用配置类对象的实现方式一，通过以下三个注解组合实现
 *                1、@Component 将配置类对象作为bean注入到spring上下文
 *                2、@PropertySource 指定配置文件
 *                3、@ConfigurationProperties 指定配置的公共前缀
 *
 *                @PropertySource 不支持yml文件
 * @Author: zhangxuefeng
 * @Date: 2019/7/15 上午11:58
 * @Version: 1.0
 **/
@Component
@ConfigurationProperties(prefix = "another.display")
@PropertySource("classpath:config/custom2.properties")
@Data
public class Custom2Properties {

    private String hello;
    private Integer number;
}
