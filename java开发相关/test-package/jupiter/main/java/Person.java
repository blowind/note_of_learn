package com.simple.jupiter;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: Person
 * @Description: Test2Assertions断言中使用的POJO
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 上午10:30
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
public class Person {
    String firstName;
    String lastName;
}
