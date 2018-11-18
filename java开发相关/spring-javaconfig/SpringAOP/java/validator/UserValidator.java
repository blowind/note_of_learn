package com.zxf.aop.validator;

import com.zxf.aop.model.User;

/**
 * @InterfaceName: UserValidator
 * @Description: UserServiceImpl功能增强接口，用于传入参数判空预处理，AOP用法
 * @Author: ZhangXuefeng
 * @Date: 2018/11/18 11:27
 * @Version: 1.0
 **/
public interface UserValidator {
    boolean validate(User user);
}
