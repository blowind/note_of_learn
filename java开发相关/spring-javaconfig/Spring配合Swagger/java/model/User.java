package com.zxf.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: User
 * @Description:
 *
 *  @ApiModel ：  描述返回对象的意义，用在返回对象类上
 *  @ApiModelProperty ： 对象属性，用在出入参数对象的字段上
 *
 * @Author: ZhangXuefeng
 * @Date: 2018/11/20 14:24
 * @Version: 1.0
 **/
@Data
@ApiModel("基本用户对象")
public class User {
    /*用在出入参数对象的字段上*/
    @ApiModelProperty("用户id")
    private int id;
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("用户备注")
    private String note;
}
