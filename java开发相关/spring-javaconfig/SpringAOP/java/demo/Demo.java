package com.zxf.aop.demo;

import com.zxf.aop.model.User;
import com.zxf.aop.service.UserService;
import com.zxf.aop.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Demo implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        System.out.println("==============正常运行的切面环绕==============");
        User user = new User();
        user.setId(1);
        user.setUsername("James Bond");
        user.setNote("Mission Impossible");
        userService.printUser(user);

        System.out.println("==============增强实现的切面环绕==============");
        UserValidator userValidator = (UserValidator)userService;
        if(userValidator.validate(user)) {
            userService.printUser(user);
        }

        System.out.println("============操作对象为空时的异常返回===========");
        userService.printUser(null);
    }
}
