package com.zxf.bootsecurity.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 5.0版本spring强制需要的密码编码器
 * @date 2018/06/09 10:15
 */
public class CustomPasswordEncoder implements PasswordEncoder{
	@Override
	public String encode(CharSequence arg0) {
		return arg0.toString();
	}

	@Override
	public boolean matches(CharSequence arg0, String arg1) {
		return Objects.equals(arg0.toString(), arg1.toString());
	}
}
