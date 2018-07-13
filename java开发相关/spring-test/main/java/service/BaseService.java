package com.springtest.service;

import org.springframework.stereotype.Service;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 基本服务bean
 * @date 2018/07/13 12:43
 */
@Service
public class BaseService {

	public String returnOK() {
		return "BaseService";
	}

}
