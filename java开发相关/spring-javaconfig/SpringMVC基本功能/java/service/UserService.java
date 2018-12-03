package com.zxf.springmvc.service;

import com.zxf.springmvc.PO.UserPO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Long id = database.size() > 0 ? database.get(database.size()-1).getId() + 1L : 1L;
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

    public List<UserPO> findUsers(String userName, String note, int start, int end) {
        List<UserPO> userPOList = database.stream()
                .filter(e -> {
                    return e.getUserName().equals(userName) && e.getNote().equals(note);
                }).collect(Collectors.toList());
        return userPOList.subList(start, end);
    }

    public int updateUser(UserPO userPO) {
        try{
            UserPO toUpdate = database.stream()
                    .filter(e -> {
                        return e.getId() == userPO.getId();
                    }).collect(Collectors.toList()).get(0);
            toUpdate.setUserName(userPO.getUserName());
            toUpdate.setNote(userPO.getNote());
            toUpdate.setSex(userPO.getSex());
            return 1;
        }catch (Exception e) {
            return 0;
        }
    }

    public int updateUserName(Long id, String userName) {
        try{
            UserPO toUpdate = database.stream()
                    .filter(e -> {
                        return e.getId().equals(id);
                    }).collect(Collectors.toList()).get(0);
            toUpdate.setUserName(userName);
            return 1;
        }catch (Exception e) {
            return 0;
        }
    }

    public int deleteUser(Long id) {
        try{
            UserPO toDelete = database.stream()
                    .filter(e -> {
                        return e.getId().equals(id);
                    }).collect(Collectors.toList()).get(0);
            database.remove(toDelete);
            return 1;
        }catch (Exception e) {
            return 0;
        }

    }
}
