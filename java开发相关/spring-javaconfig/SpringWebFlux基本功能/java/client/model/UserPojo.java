package com.zxf.spring.webflux.client.model;

import lombok.Data;

/**
 *  @ClassName: UserPojo
 *  @Description: 客户端上使用的User对象
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/24 15:40
 *  @Version: 1.0
 **/
@Data
public class UserPojo {
	private Long id;
	private String userName;
	private int sex = 1;
	private String note = null;
}
