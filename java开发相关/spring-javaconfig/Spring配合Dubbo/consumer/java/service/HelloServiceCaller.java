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

    /* 当producer设置注册地址为N/A，即不指定注册中心时，通过此处指定的url中协议dubbo、地址、端口来强行建立与producer的连接*/
//    @Reference(url = "dubbo://127.0.0.1:12345")
    @Reference
    private HelloService helloService;

    public String doSayHello(String name) {
        return helloService.sayHello(name);
    }
}
