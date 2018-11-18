package com.zxf.aop.validator.impl;

import com.zxf.aop.model.User;
import com.zxf.aop.validator.UserValidator;

/**
 * @ClassName: UserValidatorImpl
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/18 11:29
 * @Version: 1.0
 **/
public class UserValidatorImpl implements UserValidator{
    @Override
    public boolean validate(User user) {
        System.out.println("+引入新的接口: " + UserValidator.class.getSimpleName());
        return user != null;
    }
}
