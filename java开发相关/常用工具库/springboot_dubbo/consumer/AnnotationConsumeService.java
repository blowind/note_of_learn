package com.zxf.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxf.dubbo.service.HelloService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AnnotationConsumeService implements ApplicationListener<ContextRefreshedEvent> {

	@Reference
	public HelloService helloService;

	public void run() {
		System.out.println("==========================================");
		String output = helloService.sayHello("in consumer now");
		System.out.println(output);
		System.out.println("==========================================");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent e) {
		this.run();
	}
}
