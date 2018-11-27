package com.zxf.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: ConsumerApplication
 * @Description: Dubbo消费者模块
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 19:00
 * @Version: 1.0
 **/
/*使用@EnableDubboConfig注解配合application.properties的配置
代替@DubboComponentScan注解配合其中的各个配置Bean，更Spring Boot风格*/
@SpringBootApplication
@EnableDubboConfig
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
