package com.zxf.spring.webflux.dao;

import com.zxf.spring.webflux.enums.SexEnum;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;

/**
 *  @ClassName: User
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/15 16:19
 *  @Version: 1.0
 **/
@Document
@Data
public class User implements Serializable {
	private static final long serialVersionUID = 8952415466022712597L;

	@Id
	private Long id;
	private SexEnum sex;
	@Field("user_name")
	private String userName;
	private String note;
}
