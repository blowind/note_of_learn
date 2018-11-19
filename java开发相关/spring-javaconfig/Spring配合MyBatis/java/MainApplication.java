package com.zxf.spring.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: SpringApplication
 * @Description: 主程序入口
 * @Author: ZhangXuefeng
 * @Date: 2018/11/19 22:43
 * @Version: 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.zxf.spring.mybatis")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
