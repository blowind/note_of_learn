package com.zxf.zxfbatis.simple.model;

import com.zxf.zxfbatis.simple.enums.SexEnum;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private SexEnum sex;
}
