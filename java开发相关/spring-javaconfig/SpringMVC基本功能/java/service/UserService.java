package com.zxf.springmvc.service;

import com.zxf.springmvc.PO.UserPO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserService
 * @Description: 模拟业务层处理数据的服务，此处不进一步调用数据库相关的DAO层服务，仅使用本地的内存变量模拟
 * @Author: ZhangXuefeng
 * @Date: 2018/12/3 0:01
 * @Version: 1.0
 **/
@Service
public class UserService {

    List<UserPO> database = new ArrayList<>();

    public UserPO insertUser(UserPO userPO) {
        Long id = database.size() + 1L;
        userPO.setId(id);
        database.add(userPO);
        return userPO;
    }

    public UserPO getUser(Long id) {
        if(database.size() <= 0)
            return null;
        for(UserPO e: database) {
            if(e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}
