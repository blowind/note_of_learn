package com.zxf.zxfspring.service.impl;

import com.zxf.zxfspring.model.Role;
import com.zxf.zxfspring.model.Role2;
import com.zxf.zxfspring.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 14:20
 */
@Slf4j
@Component
@Primary /*指定默认注入bean，在spring不能明确接口或者类的具体注入bean时生效*/
public class RoleServiceImpl2 implements RoleService{

	private Role role;

	@Autowired
	@Qualifier("roleIndex2")  /*通过Bean的名称来指定注入的Bean*/
	private Role2 role2;

	@Autowired   /*Autowired注解用于set函数进行注入*/
	public void setRole(Role role) {
		this.role = role;
	}

	/*
	Autowired注解用于构造函数进行注入
	public RoleServiceImpl2(@Autowired Role role) {
		this.role = role;
	}*/

	public void printRoleInfo() {
		log.info("【RoleServiceImpl2】【role】id={}, roleName={}, note={}", role.getId(), role.getRoleName(), role.getNote());
		log.info("【RoleServiceImpl2】【role2】id={}, roleName={}, note={}", role2.getId(), role2.getRoleName(), role2.getNote());
	}
}
