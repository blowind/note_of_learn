package com.zxf.spring.webflux.repository;

import com.zxf.spring.webflux.dao.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 *  @InterfaceName: UserRepository
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/15 16:27
 *  @Version: 1.0
 **/
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, Long> {
	Flux<User> findByUserNameLikeAndNoteLike(String userName, String note);
}
