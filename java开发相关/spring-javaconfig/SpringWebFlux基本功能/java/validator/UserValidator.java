package com.zxf.spring.webflux.validator;

import com.zxf.spring.webflux.dao.User;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *  @ClassName: UserValidator
 *  @Description: 验证Controller中传入的User对象是否有效
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/24 11:16
 *  @Version: 1.0
 **/
public class UserValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		if(StringUtils.isEmpty(user.getUserName())) {
			errors.rejectValue("userName", null, "用户名不能为空");
		}
	}
}
