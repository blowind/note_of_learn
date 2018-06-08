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
// 注意此处Serializable不能少，除非在template配置时显示指定序列化对象，否则所有放到redis里面的对象都要实现本接口
public class Person implements Serializable {

	private String id;
	private String name;
	private Integer age;

}
