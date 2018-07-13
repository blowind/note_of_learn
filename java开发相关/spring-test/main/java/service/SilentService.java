package com.springtest.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 没激活的Service
 * @date 2018/07/13 18:04
 */
@Service
@Profile("production")
public class SilentService {
	public String getName() {
		return "SilentService";
	}
}
