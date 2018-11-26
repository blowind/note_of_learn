package com.zxf.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: MongoDBApplication
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 22:04
 * @Version: 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.zxf.mongodb")
public class MongoDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongoDBApplication.class, args);
    }
}
