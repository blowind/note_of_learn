package com.zxf.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: MainApplication
 * @Description: 主程序入口
 * @Author: ZhangXuefeng
 * @Date: 2018/11/20 14:12
 * @Version: 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.zxf.swagger")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
