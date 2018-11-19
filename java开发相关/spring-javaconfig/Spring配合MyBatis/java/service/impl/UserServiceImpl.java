package com.zxf.spring.mybatis.service.impl;

import com.zxf.spring.mybatis.dao.UserMapper;
import com.zxf.spring.mybatis.model.User;
import com.zxf.spring.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserServiceImpl
 * @Description: UserService服务的具体实现类
 * @Author: ZhangXuefeng
 * @Date: 2018/11/19 23:41
 * @Version: 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(Integer id) {
        return userMapper.getUser(id);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }
}
