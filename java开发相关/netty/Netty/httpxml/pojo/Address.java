package com.zxf.nio.httpxml.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: Address
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/1 11:39
 * @Version: 1.0
 **/
@Data
public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String postCode;
    private String country;
}
