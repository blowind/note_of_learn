package com.zxf.nio.httpxml.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName: Customer
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/1 11:38
 * @Version: 1.0
 **/
@Data
public class Customer {
    private long customerNumber;
    private String firstName;
    private String lastName;
    private List<String> middleNames;
}
