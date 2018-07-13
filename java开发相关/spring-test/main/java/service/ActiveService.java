package com.springtest.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 测试profile标签的控制
 * @date 2018/07/13 18:02
 */
@Service
@Profile("dev")
public class ActiveService {

	public String getName() {
		return "ActiveService";
	}
}
