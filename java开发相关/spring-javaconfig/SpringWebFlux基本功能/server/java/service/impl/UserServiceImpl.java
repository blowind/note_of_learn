package com.zxf.spring.webflux.service.impl;

import com.zxf.spring.webflux.dao.User;
import com.zxf.spring.webflux.repository.UserRepository;
import com.zxf.spring.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *  @ClassName: UserServiceImpl
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/15 16:32
 *  @Version: 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public Mono<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public Mono<User> insertUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Mono<User> updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Mono<Void> deleteUser(Long id) {
		Mono<Void> result = userRepository.deleteById(id);
		return result;
	}

	@Override
	public Flux<User> findUsers(String userName, String note) {
		return userRepository.findByUserNameLikeAndNoteLike(userName, note);
	}
}
