package com.zxf.springmvc.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: NotFoundException
 * @Description: 定义控制器处理失败的异常
 * @Author: ZhangXuefeng
 * @Date: 2018/12/3 23:37
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotFoundException extends RuntimeException {
    private Long code;
    private String customMessage;
}
