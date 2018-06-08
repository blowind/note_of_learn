package com.zxf.bootredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description redis中存储数据实验模型
 * @date 2018/06/07 11:31
 */
@Data
@AllArgsConstructor
public class City implements Serializable{
	private String name;
	private String province;
	private Integer postcode;
}
