package com.zxf.spring.webflux.vo;

import lombok.Data;

/**
 *  @ClassName: UserVo
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/15 16:57
 *  @Version: 1.0
 **/
@Data
public class UserVo {
	private Long id;
	private String userName;
	private int sexCode;
	private String sexName;
	private String note;
}
