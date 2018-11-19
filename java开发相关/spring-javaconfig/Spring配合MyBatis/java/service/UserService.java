package com.zxf.spring.mybatis.service;

import com.zxf.spring.mybatis.model.User;

/**
 * @InterfaceName: UserService
 * @Description: User信息对外服务接口
 * @Author: ZhangXuefeng
 * @Date: 2018/11/19 23:40
 * @Version: 1.0
 **/
public interface UserService {
    User getUser(Integer id);

    int addUser(User user);
}
