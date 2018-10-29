package com.zxf.zxfspring.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 14:04
 */
@Data
@Slf4j
@Component("role")
/*指定Bean的作用域为原型，即每次IOC容器获取Bean都创建新实例*/
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Role{
	@Value("1")
	private Long id;

	@Value("role_name_1")
	private String roleName;

	@Value("role_note_1")
	private String note;

	public void sayHello() {
		log.info("hello, i'm {}", this.toString());
	}
}
