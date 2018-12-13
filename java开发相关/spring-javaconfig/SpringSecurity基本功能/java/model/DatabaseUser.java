package com.zxf.spring.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  @ClassName: User
 *  @Description:  注意，此处实现的属性名和表字段名，要严格按照下划线和字母大写的转换关系来，否则映射不上，报错
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/13 17:33
 *  @Version: 1.0
 **/
@Data
@Entity
@Table(name = "t_user")
public class DatabaseUser {
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private String userName;
	private String pwd;
	private Integer available;
	private String note;
}
