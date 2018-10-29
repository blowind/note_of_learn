package com.zxf.zxfspring.service.impl;

import com.zxf.zxfspring.model.Role;
import com.zxf.zxfspring.model.Role2;
import com.zxf.zxfspring.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 14:06
 */
@Slf4j
@Component
public class RoleServiceImpl implements RoleService{

	/*当寻找不到Role类型的Bean注入时不抛异常，继续运行*/
	@Autowired(required = false)
	private Role2 role;

	public void printRoleInfo() {
		log.info("【in RoleServiceImpl】id={}, roleName={}, note={}", role.getId(), role.getRoleName(), role.getNote());
	}
}
