package com.zxf.run;

import com.zxf.aware.AwareConfig;
import com.zxf.aware.AwareService;
import com.zxf.el.ElConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AwareConfig.class);

		AwareService awareService = context.getBean(AwareService.class);
		awareService.outputResult();

		context.close();
	}
}
