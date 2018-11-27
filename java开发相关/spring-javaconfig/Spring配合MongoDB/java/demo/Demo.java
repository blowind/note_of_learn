package com.zxf.mongodb.demo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zxf.mongodb.model.Role;
import com.zxf.mongodb.model.User;
import com.zxf.mongodb.service.UserRepository;
import com.zxf.mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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

    @Autowired
    private UserRepository userRepository;

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

    private void deleteUser(Long id) {
        DeleteResult result = userService.deleteUser(id);
        System.out.println(result.toString());
        return;
    }

    private void updateUser(Long id) {
        UpdateResult result =  userService.updateUser(id, "new_name_after_updated", "new_note_after_updated");
        System.out.println(result.toString());
        return;
    }

    private void listUsers() {
        List<User> userList =  userRepository.findByUserNameLike("name");
        for(User u: userList) {
            System.out.println(u.toString());
        }
    }

    private void useFindQuery() {
        User user = userRepository.find(1L, "user_name_1");
        if(user != null) {
            System.out.println(user.toString());
        }else{
            System.out.println("====== user is null =======");
        }
    }

    private void findByConventionImpl() {
        User user = userRepository.findUserByIdOrUserName(3L, "user_name_1");
        if(user != null) {
            System.out.println(user.toString());
        }else{
            System.out.println("====== user is null =======");
        }
    }

    @Autowired
    public void onApplicationEvent(ContextRefreshedEvent e) {
//        addUser();
//        updateUser(1L);
//        listUsers();
//        deleteUser(1L);
//        useFindQuery();
        findByConventionImpl();
    }
}
