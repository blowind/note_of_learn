package com.zxf.mongodb.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @ClassName: Role
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 22:17
 * @Version: 1.0
 **/
@Document
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 4703323081578078173L;

    private Long id;
    @Field("role_name")
    private String roleName = null;
    private String note = null;

}
