package com.zxf.producer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxf.common.dubbo.HelloService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: HelloServiceImpl
 * @Description: 作为服务提供方的类，使用Dubbo的@Service注解后，会被作为Bean放入Spring容器，可以通过context获取
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 19:09
 * @Version: 1.0
 **/
@Service(timeout = 5000, interfaceClass = HelloService.class)
@Transactional
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
