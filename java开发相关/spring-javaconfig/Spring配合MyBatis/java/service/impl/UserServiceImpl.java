package com.zxf.spring.mybatis.service.impl;

import com.zxf.spring.mybatis.dao.UserMapper;
import com.zxf.spring.mybatis.model.User;
import com.zxf.spring.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    /*
    isolation：事务隔离级别
    propagation：传播行为
    timeout：事物超时时间，单位为秒
    readOnly：是否只读事务
    */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,
                    propagation = Propagation.NESTED,
                    timeout = 60,
                    readOnly = false)
    public int addUser(User user) {
        return userMapper.insert(user);
    }
}
