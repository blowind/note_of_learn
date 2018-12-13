package com.zxf.spring.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  @ClassName: DatabaseRole
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/13 17:37
 *  @Version: 1.0
 **/
@Data
@Entity
@Table(name = "t_role")
public class DatabaseRole {
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private String roleName;
	private String note;
}
