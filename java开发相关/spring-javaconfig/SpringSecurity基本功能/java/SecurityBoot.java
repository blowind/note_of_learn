package com.zxf.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @ClassName: SecurityBoot
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/12/4 11:06
 * @Version: 1.0
 **/
@SpringBootApplication
@EnableJpaRepositories("com.zxf.spring.security")
//@EnableWebSecurity    /*启用Web端Secuirty功能的注解，引入spring-boot的security包和web包即生效，不需显示声明，此处仅做展示*/
//@EnableGlobalAuthentication   /*启用非web端Secuirty功能的注解，引入spring-boot的security包即生效，不需显示声明，此处仅做展示*/
public class SecurityBoot {
    public static void main(String[] args) {
        SpringApplication.run(SecurityBoot.class, args);
    }
}
