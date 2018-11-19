package com.zxf.spring.mybatis.demo;

import com.zxf.spring.mybatis.enums.SexEnum;
import com.zxf.spring.mybatis.model.User;
import com.zxf.spring.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Demo
 * @Description: 功能演示类
 * @Author: ZhangXuefeng
 * @Date: 2018/11/19 23:42
 * @Version: 1.0
 **/
@Component
public class Demo implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        User user = new User();
        user.setUserName("No.1");
        user.setNote("就试试看");
        user.setSex(SexEnum.MALE);
        userService.addUser(user);
    }
}
