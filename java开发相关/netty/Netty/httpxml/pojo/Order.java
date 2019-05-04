package com.zxf.nio.httpxml.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: Order
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/1 11:37
 * @Version: 1.0
 **/
@Data
public class Order {
    private long orderNumber;
    private Customer customer;
    private Address billTo;
    private Shipping shipping;
    private Address shipTo;
    private Float total;
}
