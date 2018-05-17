package com.zxf.dubbo;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.zxf.dubboc")
public class DubbocApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubbocApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner run(AnnotationConsumeService service) {
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//				service.run();
//			}
//		};
//	}
}
