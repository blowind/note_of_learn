package com.zxf.springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: User
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/29 22:56
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userName;
    private String note;
}
