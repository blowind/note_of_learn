package com.zxf.zxfspring.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 14:26
 */
@Data
@Slf4j
@Component("roleIndex2")
public class Role2{
	@Value("2")
	private Long id;

	@Value("role_name_2")
	private String roleName;

	@Value("role_note_2")
	private String note;

	public void sayHello() {
		log.info("hello, i'm {}", this.toString());
	}
}
