package com.zxf.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: User
 * @Description: 基本的文档对象
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 22:13
 * @Version: 1.0
 **/
@Document  /*表明本对象作为MongoDB的文档存在*/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 8324791264813087490L;

    @Id  /*文档编号，主键*/
    private Long id;

    @Field("user_name")   /*对于数据库下划线和JAVA属性命名差异，用Field注解配置*/
    private String userName = null;

    private String note = null;

    private List<Role> roles = null;

}
