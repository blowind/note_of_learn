package com.zxf.spring.webflux.client;

import com.zxf.spring.webflux.client.model.UserPojo;
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
//		newUser.setUserName("user_name_new_1");
		newUser.setSex(SexEnum.FEMALE);

//		insertUser(client, newUser);
		insertUserWithValidator(client, newUser);
//		deleteUser(client, 1L);

//		insertUserByParamString(client);

		/*此处更新是更新全部内容，所以要指定全部内容，否则不指定内容不会存在*/
//		User updateUser = new User();
//		updateUser.setId(1L);
//		updateUser.setUserName("user_name_new_1");
//		updateUser.setNote("note_new_1");
//		updateUser.setSex(SexEnum.MALE);
//		updateUser(client, updateUser);

//		findUsers(client, "user", "note");


	}

	/**
	 *  基本的客户端查询示例
	 * @param client
	 * @param id
	 */
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

	/**
	 * 带错误处理的客户端查询用户示例
	 * @param client
	 * @param id
	 */
	private static void getUser2(WebClient client, Long id) {
		Mono<UserVo> userMono = client.get()
										.uri("/user/{id}", id)
										.accept(MediaType.APPLICATION_STREAM_JSON)
										.retrieve().onStatus(
							/*发生4开头或者5开头的状态码，则执行第二个lambda表达式*/
						status -> status.is4xxClientError() || status.is5xxServerError(),
							/*发生异常时，使用下面的表达式作为返回结果*/
						response -> Mono.empty())
										.bodyToMono(UserVo.class);
		UserVo userVo = userMono.block();
		if(userVo != null) {
			System.out.println("用户名称: " + userVo.getUserName());
		}else{
			System.out.println("服务器没有返回编号为:" + id + "的用户");
		}
	}

	private static UserPojo translate(UserVo vo) {
		if(vo == null) {
			return null;
		}
		UserPojo pojo = new UserPojo();
		pojo.setId(vo.getId());
		pojo.setUserName(vo.getUserName());
		pojo.setSex(vo.getSexCode());
		pojo.setNote(vo.getNote());
		return pojo;
	}

	/**
	 * 客户端处将服务端返回的对象转换为客户端本地使用的UserPojo对象
	 * @param client
	 * @param id
	 */
	private static void getUserPojo(WebClient client, Long id) {
		Mono<UserPojo> userMono = client.get()
										.uri("/user/{id}", id)
										.accept(MediaType.APPLICATION_STREAM_JSON)
										/*启用交换，而不是之前使用的retrieve() */
										.exchange()
										/*出现错误则返回空*/
										.doOnError(ex -> Mono.empty())
										/*获取服务器发送的UserVo对象*/
										.flatMap(response -> response.bodyToMono(UserVo.class))
										/*通过自定义的方法转换为客户端使用的UserPojo对象*/
										.map(ClientMain::translate);
		UserPojo pojo = userMono.block();
		if(pojo != null) {
			System.out.println("用户名称: " + pojo.getUserName());
		}else{
			System.out.println("返回编号为:" + id + "的用户失败");
		}
	}

	/**
	 *  基本的客户端数据插入示例
	 * @param client
	 * @param newUser
	 */
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

	/**
	 * 客户端验证服务端使用Controller附带的转换器插入参数为字符串形式的用户示例
	 * @param client
	 */
	private static void insertUserByParamString(WebClient client) {
		Mono<UserVo> userMono = client.post()
								.uri("/user2/{user}", "3-convert-2-note")
								.accept(MediaType.APPLICATION_STREAM_JSON)
								.retrieve()
								.bodyToMono(UserVo.class);
		UserVo userVo = userMono.block();
		System.out.println("用户名称: " + userVo.getUserName());
	}

	/**
	 * 客户端验证服务端使用Validator拦截待插入用户信息并进行验证示例
	 * @param client
	 * @param newUser
	 */
	private static void insertUserWithValidator(WebClient client, User newUser) {
		Mono<UserVo> userMono = client.post()
								.uri("/user3")
								.contentType(MediaType.APPLICATION_STREAM_JSON)
								.body(Mono.just(newUser), User.class)
								.accept(MediaType.APPLICATION_STREAM_JSON)
								.retrieve()
								.bodyToMono(UserVo.class);
		UserVo userVo = userMono.block();
		System.out.println("用户名称: " + userVo.getUserName());
	}

	/**
	 * 客户端更新用户部分信息示例
	 * @param client
	 * @param updateUser
	 */
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

	/**
	 * 客户端设置http请求头来传递参数
	 * @param client
	 * @param id
	 * @param userName
	 */
	private static void updateUserName(WebClient client, Long id, String userName) {
		Mono<UserVo> mono = client.put()
								.uri("/user/name", userName)
								/*设置HttpHeader，此处有个Long到String的转换*/
								.header("id", id+"")
								/*设置HttpHeader*/
								.header("userName", userName)
								.accept(MediaType.APPLICATION_STREAM_JSON)
								.retrieve()
								.onStatus(
						status -> status.is4xxClientError() || status.is5xxServerError(),
						response -> Mono.empty())
								.bodyToMono(UserVo.class);
		UserVo userVo = mono.block();
		if(userVo != null) {
			System.out.println("用户名称: " + userVo.getUserName());
		}else{
			System.out.println("返回编号为:" + id + "的用户失败");
		}
	}

	/**
	 * 客户端查询批量数据示例
	 * @param client
	 * @param userName
	 * @param note
	 */
	private static void findUsers(WebClient client, String userName, String note) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("note", note);
		/*定义PUT请求，此处展示使用Map传递多个参数，也可以使用罗列所有参数的方式，参数过多时比较麻烦*/
		Flux<UserVo> userVoFlux = client.get().uri("/user/{userName}/{note}", paramMap)
									.accept(MediaType.APPLICATION_STREAM_JSON)
									.retrieve()
									.bodyToFlux(UserVo.class);
		/*通过Iterator遍历结果数据流，此处才会发送请求，执行后服务器才会响应*/
		/*一种下拉式的获取，即在每一次执行循环时，才从服务器拉取一个数据流序列到客户端处理，而不是一次获取所有符合要求的序列*/
		Iterator<UserVo> iterator = userVoFlux.toIterable().iterator();
		while(iterator.hasNext()) {
			UserVo userVo = iterator.next();
			System.out.println("用户名称: " + userVo.getUserName());
		}
	}

	/**
	 * 客户端删除用户示例
	 * @param client
	 * @param id
	 */
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
