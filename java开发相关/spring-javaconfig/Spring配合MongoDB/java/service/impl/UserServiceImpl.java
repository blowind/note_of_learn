package com.zxf.mongodb.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zxf.mongodb.model.User;
import com.zxf.mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 22:30
 * @Version: 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MongoTemplate mongoTemplate;

    /*根据id精确查找一个元素*/
    @Override
    public User getUser(Long id) {
        return mongoTemplate.findById(id, User.class);

        /*如果只需要获取第一个元素，也可以采用如下查询方法*/
//        Criteria criteriaId = Criteria.where("id").is(id);
//        Query queryId = Query.query(criteriaId);
//        return mongoTemplate.findOne(queryId, User.class);
    }

    /*批量查找*/
    @Override
    public List<User> findUser(String userName, String note, int skip, int limit) {
        Criteria criteria = Criteria.where("userName").regex(userName).and("note").regex(note);
        Query query = Query.query(criteria).limit(limit).skip(skip);
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

    /*如果MongoDFB中存在id相同的对象，就更新其属性；否则新增*/
    @Override
    public void saveUser(User user) {
        /*使用名称为user的文档保存用户信息*/
        mongoTemplate.save(user, "user");
        /*如果文档采用类名首字符小写，也可以如下使用*/
        /*mongoTemplate.save(user);*/
    }

    @Override
    public DeleteResult deleteUser(Long id) {
        Criteria criteriaId = Criteria.where("id").is(id);
        Query query = Query.query(criteriaId);
        DeleteResult result = mongoTemplate.remove(query, User.class);
        return result;
    }

    @Override
    public UpdateResult updateUser(Long id, String userName, String note) {
        /*确定要更新的对象*/
        Criteria criteriaId = Criteria.where("id").is(id);
        Query query = Query.query(criteriaId);
        /*定义更新对象，通过Update的构造方法设置对userName的更新，通过set方法设置对note的更新*/
        Update update = Update.update("userName", userName);
        update.set("note", note);

        /*更新满足要求的第一个文档*/
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        /*更新满足要求的多个文档*/
        /*UpdateResult result = mongoTemplate.updateMulti(query, update, User.class);*/
        return result;

    }
}
