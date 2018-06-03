package com.zxf.bootredis.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 领域模型
 * @date 2018/06/03 14:21
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person implements Serializable {

	private String id;
	private String name;
	private Integer age;

}
