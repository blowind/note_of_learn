package com.zxf.bootredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 测试Jackson序列化方法的领域类
 * @date 2018/06/07 19:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book { // 因为使用Book对象的redisTemplate使用配置了序列化工具，因此此处不需要实现Serializable接口
	private String name;
	private Long ISBN;
	private Double price;
}
