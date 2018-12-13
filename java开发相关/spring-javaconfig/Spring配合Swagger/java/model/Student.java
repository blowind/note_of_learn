package com.zxf.swagger.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 *  @ClassName: Student
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/13 13:45
 *  @Version: 1.0
 **/
@Data
@ApiModel(parent = User.class)  /*用在返回对象类上*/
public class Student extends User {
	private String school;
}
