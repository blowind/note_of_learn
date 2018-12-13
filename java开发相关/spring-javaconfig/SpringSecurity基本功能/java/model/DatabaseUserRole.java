package com.zxf.spring.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  @ClassName: DatabaseUserRole
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/13 19:31
 *  @Version: 1.0
 **/
@Data
@Entity
@Table(name = "t_user_role")
public class DatabaseUserRole {
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private Integer roleId;
	private Integer userId;
}
