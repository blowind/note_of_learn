package com.zxf.mongodb.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zxf.mongodb.model.User;

import java.util.List;

/**
 * @ClassName: UserService
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 22:27
 * @Version: 1.0
 **/
public interface UserService {
    void saveUser(User user);

    DeleteResult deleteUser(Long id);

    List<User> findUser(String userName, String note, int skip, int limit);

//    UpdateResult updateUser(Long id, String userName, String note);
//
    User getUser(Long id);

}
