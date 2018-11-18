package com.zxf.aop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName: AopConfig
 * @Description: 通过@EnableAspectJAutoProxy启动Spring自动的切面代理功能（全项目配置一次）
 * @Author: ZhangXuefeng
 * @Date: 2018/11/18 11:52
 * @Version: 1.0
 **/
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
}
