package com.simple.jupiter;

import java.util.stream.Stream;

/**
 * @ClassName: Test10StringProviders
 * @Description: 外部类工厂方法类，用于给@MethodSource提供入参流
 * @Author: ZhangXuefeng
 * @Date: 2019/6/22 20:51
 * @Version: 1.0
 **/
public class Test10StringProviders {

    static Stream<String> tinyStrings() {
        return Stream.of(".", "oo", "000");
    }
}
