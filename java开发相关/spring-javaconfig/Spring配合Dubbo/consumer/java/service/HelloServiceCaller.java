package com.zxf.consumer.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxf.common.dubbo.HelloService;
import org.springframework.stereotype.Component;

/**
 * @ClassName: HelloServiceCaller
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 19:19
 * @Version: 1.0
 **/
@Component   /*引用Dubbo服务的类一定要作为Bean注册到Spring容器*/
public class HelloServiceCaller {

    @Reference(url = "dubbo://127.0.0.1:12345")
    private HelloService helloService;

    public String doSayHello(String name) {
        return helloService.sayHello(name);
    }
}
