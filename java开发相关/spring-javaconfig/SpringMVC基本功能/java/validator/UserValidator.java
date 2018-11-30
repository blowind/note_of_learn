package com.zxf.springmvc.validator;

import com.zxf.springmvc.model.User;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @ClassName: UserValidator
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/30 15:34
 * @Version: 1.0
 **/
/*通过@InitBinder进行绑定*/
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> var1) {
        return var1.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null) {
            errors.rejectValue("", null, "用户不能为空");
            return;
        }
        User user = (User)target;
        if(StringUtils.isEmpty(user.getUserName())) {
            errors.rejectValue("userName", null, "用户名不能为空");
        }
    }
}
