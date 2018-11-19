package com.zxf.mybatis.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: SpringBootApp
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/14 19:20
 * @Version: 1.0
 **/
@SpringBootApplication
@Configuration("com.zxf")
public class SpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);

    }
}
