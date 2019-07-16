package com.zxf.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: SpringMain
 * @Description:  resources目录下的application.properties文件是主动识别并加载，
 *                其他名字的要使用@PropertySource指定
 *
 *                对比项     @ConfigurationProperties               @Value
 *                注解功能   将配置文件中的属性值批量注入类的各个属性     为类中的各个属性逐个赋值
 *                松散绑定   支持                                    不支持
 *                SpEL      不支持                                  支持
 *                JSR303数据校验      支持                           不支持
 *                复杂类型封装         支持                           不支持
 * @Author: zhangxuefeng
 * @Date: 2019/7/12 下午3:09
 * @Version: 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.zxf.spring")
@PropertySource("classpath:config/custom.properties")
public class SpringMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);
    }
}
