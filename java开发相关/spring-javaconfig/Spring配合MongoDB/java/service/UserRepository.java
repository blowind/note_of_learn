package com.zxf.mongodb.service;

import com.zxf.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: UserRepository
 * @Description: 通过JPA扩展来使用mongodb，需要配合@EnableMongoRepositories注解
 *   JPA的好处是只需要定义接口，根据接口方法进行扩展，无需实现接口方法
 *   同时指定的T类型必须要使用@Ducument注解过，此处是User类，主键类型根据实际指定，即类中@Id注解的属性类型
 * @Author: ZhangXuefeng
 * @Date: 2018/11/27 22:39
 * @Version: 1.0
 **/
@Repository  /*注意注解不能少*/
public interface UserRepository extends MongoRepository<User, Long> {
    /*符合JPA命名规范，不再需要实现该方法*/
    List<User> findByUserNameLike(String userName);

    /*使用JSON而不是函数名自定义查询，不需要提供实现方法，必须配合MongoRepository使用， ?0为参数占位符*/
    @Query("{'id': ?0, 'userName': ?1}")
    User find(Long id, String userName);

    /*只想自定义方法findUserByIdOrUserName的实现时，实现一个接口名添加后缀Impl的类，并且将该类注解为@Repository即可，其他方法仍然沿用MongoRepository的默认实现*/
    User findUserByIdOrUserName(Long id, String userName);
}
