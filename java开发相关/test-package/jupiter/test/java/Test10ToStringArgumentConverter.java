package com.simple.jupiter;

import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @ClassName: Test10ToStringArgumentConverter
 * @Description: 测试方法参数显示类型转换类
 * @Author: ZhangXuefeng
 * @Date: 2019/6/23 0:47
 * @Version: 1.0
 **/
public class Test10ToStringArgumentConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) {
        assertEquals(String.class, targetType, "Can only convert  to string");
        return String.valueOf(source);
    }
}
