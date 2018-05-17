package com.zxf.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Service(timeout = 5000, interfaceClass = HelloService.class)
@Component
public class HelloServiceImpl implements HelloService {
	public String sayHello(String name) {
		return "Hello " + name;
	}
}
