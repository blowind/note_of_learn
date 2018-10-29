package com.zxf.zxfspring;

import com.zxf.zxfspring.config.AppConfig;
import com.zxf.zxfspring.service.RoleService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 14:02
 */
public class SpringMain {

	public static void main(String[] argv) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		context.start();
		RoleService service = (RoleService)context.getBean(RoleService.class);
		service.printRoleInfo();

		/*通过环境得到properties文件中配置的值，需在AppConfig中配置PropertySource*/
		String url = context.getEnvironment().getProperty("jdbc.database.url");
	}
}
