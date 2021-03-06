package com.zxf.spring.webflux.controller;

import com.zxf.spring.webflux.dao.User;
import com.zxf.spring.webflux.service.UserService;
import com.zxf.spring.webflux.validator.UserValidator;
import com.zxf.spring.webflux.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

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

	/*在HTTP的Body里面使用JSON传递参数*/
	@PostMapping("/user")
	public Mono<UserVo> insertUser(@RequestBody User user) {
		return userService.insertUser(user).map(u -> translate(u));
	}

	/*在路径变量中通过字符串来传递参数*/
	@PostMapping("/user2/{user}")
	public Mono<UserVo> insertUserByParamString(@PathVariable("user") User user) {
		return userService.insertUser(user).map(u -> translate(u));
	}

	/*加入局部验证器，即该验证器仅对本Controller有效*/
	/*@InitBinder
	public void initBinder(DataBinder binder) {
		binder.setValidator(new UserValidator());
	}*/

	@PostMapping("/user3")
	public Mono<UserVo> insertUserWithValidator(@Valid @RequestBody User user) {
		return userService.insertUser(user).map(u -> translate(u));
	}

	@PutMapping("/user")
	public Mono<UserVo> updateUser(@RequestBody User user) {
		return userService.updateUser(user).map(u -> translate(u));
	}

	@PutMapping("/user/name")
	public Mono<UserVo> updateUserName(@RequestHeader("id") Long id, @RequestHeader("userName") String userName) {
		Mono<User> userMono = userService.getUser(id);
		User user = userMono.block();
		if(user == null) {
			throw new RuntimeException("找不到用户");
		}
		user.setUserName(userName);
		return this.updateUser(user);
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
