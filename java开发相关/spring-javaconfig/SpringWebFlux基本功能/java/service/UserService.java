package com.zxf.spring.webflux.service;

import com.zxf.spring.webflux.dao.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *  @InterfaceName: UserService
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/15 16:30
 *  @Version: 1.0
 **/
public interface UserService {

	Mono<User> getUser(Long id);

	Mono<User> insertUser(User user);

	Mono<User> updateUser(User user);

	Mono<Void> deleteUser(Long id);

	Flux<User> findUsers(String userName, String note);
}
