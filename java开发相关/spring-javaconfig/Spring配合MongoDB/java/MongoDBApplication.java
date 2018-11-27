package com.zxf.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @ClassName: MongoDBApplication
 * @Description:  repositoryImplementationPostfix指定定制化Repository部分实现的类的后缀
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 22:04
 * @Version: 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.zxf.mongodb")
@EnableMongoRepositories(basePackages = "com.zxf.mongodb.service", repositoryImplementationPostfix = "Impl")
public class MongoDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongoDBApplication.class, args);
    }
}
