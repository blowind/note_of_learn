package com.zxf.mongodb.demo;

import com.mongodb.client.result.DeleteResult;
import com.zxf.mongodb.model.Role;
import com.zxf.mongodb.model.User;
import com.zxf.mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @ClassName: Demo
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 22:25
 * @Version: 1.0
 **/
@Component
public class Demo implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserService userService;

    private void addUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("user_name_1");
        user.setNote("note_of_1");

        Role role1 = new Role();
        role1.setId(1L);
        role1.setRoleName("role_11");
        role1.setNote("note_of_role_1");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setRoleName("role_22");
        role2.setNote("note_of_role_2");

        user.setRoles(Arrays.asList(role1, role2));
        userService.saveUser(user);
    }

    private DeleteResult deleteUser(Long id) {
        return userService.deleteUser(id);
    }

    @Autowired
    public void onApplicationEvent(ContextRefreshedEvent e) {
        DeleteResult result = deleteUser(1L);
        System.out.println("1111");
    }
}
