package com.zxf.mongodb.service.impl;


import com.zxf.mongodb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserRepositoryImpl
 * @Description: 注意此处没有显示扩展接口UserRepository，如果这么做要实现UserRepository定义的所有接口，违背了只自定义findUserByIdOrUserName方法的初衷
 * @Author: ZhangXuefeng
 * @Date: 2018/11/27 23:01
 * @Version: 1.0
 **/
@Repository  /*定义为数据访问层的注解@Repository不能少*/
public class UserRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    /*注意此处函数签名要与接口中一模一样，而且public不能少*/
    public User findUserByIdOrUserName(Long id, String userName) {
        System.out.println("=====调用了默认约定的实现====");
        Criteria criteriaId = Criteria.where("id").is(id);
        Criteria criteriaName = Criteria.where("userName").is(userName);
        Criteria criteria = new Criteria();
        criteria.orOperator(criteriaId, criteriaName);
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, User.class);
    }
}
