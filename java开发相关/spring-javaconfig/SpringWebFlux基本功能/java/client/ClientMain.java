package com.zxf.spring.webflux.client;

import com.zxf.spring.webflux.dao.User;
import com.zxf.spring.webflux.enums.SexEnum;
import com.zxf.spring.webflux.vo.UserVo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *  @ClassName: ClientMain
 *  @Description: 使用上基本都是使用Mono
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/16 10:25
 *  @Version: 1.0
 **/
public class ClientMain {
	public static void main(String[] args) {
		WebClient client = WebClient.create("http://localhost:8080");
		User newUser = new User();
		newUser.setId(1L);
		newUser.setNote("note_new_1");
		newUser.setUserName("user_name_new_1");
		newUser.setSex(SexEnum.FEMALE);

//		insertUser(client, newUser);
//		deleteUser(client, 1L);

		/*此处更新是更新全部内容，所以要指定全部内容，否则不指定内容不会存在*/
		User updateUser = new User();
		updateUser.setId(1L);
		updateUser.setUserName("user_name_new_1");
		updateUser.setNote("note_new_1");
		updateUser.setSex(SexEnum.MALE);
		updateUser(client, updateUser);

		findUsers(client, "user", "note");
	}

	private static void insertUser(WebClient client, User newUser) {
		/*此处仅仅是定义一个事件，不会发送请求*/
		Mono<UserVo> userMono = client.post()
								/*设置请求URI*/
								.uri("/user")
								/*设置请求体为JSON数据流*/
								.contentType(MediaType.APPLICATION_STREAM_JSON)
								/*请求体内容*/
								.body(Mono.just(newUser), User.class)
								/*接收请求结果类型*/
								.accept(MediaType.APPLICATION_STREAM_JSON)
								/*设置请求结果检索规则*/
								.retrieve()
								/*将结果体转换为一个Mono封装的数据流*/
								.bodyToMono(UserVo.class);

		/*获取服务器发布的数据流，此处才会发送请求*/
		UserVo userVo = userMono.block();
		System.out.println("用户名称: " + userVo.getUserName());
	}

	private static void getUser(WebClient client, Long id) {
		/*定义GET请求*/
		Mono<UserVo> userVoMono = client.get()
									/*定义请求URI和参数*/
									.uri("/user/{id}", id)
									/*接收请求结果类型*/
									.accept(MediaType.APPLICATION_STREAM_JSON)
									/*设置请求结果检索规则*/
									.retrieve()
									/*将结果体转换为一个Mono封装的数据流*/
									.bodyToMono(UserVo.class);

		/*获取服务器发布的数据流，此处才会发送请求*/
		UserVo userVo = userVoMono.block();
		System.out.println("用户名称: " + userVo.getUserName());
	}

	private static void updateUser(WebClient client, User updateUser) {
		/*定义PUT请求*/
		Mono<UserVo> userVoMono = client.put().uri("/user")
									/*请求体为JSON数据流*/
									.contentType(MediaType.APPLICATION_STREAM_JSON)
									/*请求体内容*/
									.body(Mono.just(updateUser), User.class)
									/*接收请求结果类型*/
									.accept(MediaType.APPLICATION_STREAM_JSON)
									/*设置请求结果检索规则*/
									.retrieve()
									/*将结果体转换为一个Mono封装的数据流*/
									.bodyToMono(UserVo.class);

		/*获取服务器发布的数据流，此处才会发送请求*/
		UserVo userVo = userVoMono.block();
		System.out.println("用户名称: " + userVo.getUserName());
	}

	private static void findUsers(WebClient client, String userName, String note) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("note", note);
		/*定义PUT请求，使用Map传递多个参数*/
		Flux<UserVo> userVoFlux = client.get().uri("/user/{userName}/{note}", paramMap)
									.accept(MediaType.APPLICATION_STREAM_JSON)
									.retrieve()
									.bodyToFlux(UserVo.class);
		/*通过Iterator遍历结果数据流，此处才会发送请求，执行后服务器才会响应*/
		Iterator<UserVo> iterator = userVoFlux.toIterable().iterator();
		while(iterator.hasNext()) {
			UserVo userVo = iterator.next();
			System.out.println("用户名称: " + userVo.getUserName());
		}
	}

	private static void deleteUser(WebClient client, Long id) {
		Mono<Void> result = client.delete().uri("/user/{id}", id)
										.accept(MediaType.APPLICATION_STREAM_JSON)
										.retrieve()
										.bodyToMono(Void.class);
		/*获取服务器发布的数据流，此时才会发送请求*/
		Void voidResult = result.block();
		System.out.println(voidResult);
	}
}
