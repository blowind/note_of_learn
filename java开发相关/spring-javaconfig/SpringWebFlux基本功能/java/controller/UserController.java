package com.zxf.spring.webflux.controller;

import com.zxf.spring.webflux.dao.User;
import com.zxf.spring.webflux.service.UserService;
import com.zxf.spring.webflux.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *  @ClassName: UserController
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/15 16:59
 *  @Version: 1.0
 **/
@RestController
public class UserController {
	@Autowired
	private UserService userService;

	private UserVo translate(User user) {
		UserVo userVo = new UserVo();
		userVo.setUserName(user.getUserName());
		userVo.setSexCode(user.getSex().getCode());
		userVo.setSexName(user.getSex().getName());
		userVo.setNote(user.getNote());
		userVo.setId(user.getId());
		return userVo;
	}


	@GetMapping("/user/{id}")
	public Mono<UserVo> getUser(@PathVariable Long id) {
		return userService.getUser(id).map(u -> translate(u));
	}

	@PostMapping("/user")
	public Mono<UserVo> insertUser(@RequestBody User user) {
		return userService.insertUser(user).map(u -> translate(u));
	}

	@PutMapping("/user")
	public Mono<UserVo> updateUser(@RequestBody User user) {
		return userService.updateUser(user).map(u -> translate(u));
	}

	@DeleteMapping("/user/{id}")
	public Mono<Void> deleteUser(@PathVariable Long id) {
		return userService.deleteUser(id);
	}

	@GetMapping("/user/{userName}/{note}")
	public Flux<UserVo> findUsers(@PathVariable String userName, @PathVariable String note) {
		return userService.findUsers(userName, note).map(u -> translate(u));
	}
}
