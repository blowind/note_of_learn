package com.zxf.producer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxf.common.dubbo.HelloService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: HelloServiceImpl
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 19:09
 * @Version: 1.0
 **/
@Service
@Transactional
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
