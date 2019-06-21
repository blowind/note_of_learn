package com.simple.jupiter;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @ClassName: Test2HamcrestAssertions
 * @Description: 使用jupiter结合第三方断言库Hamcrest使用
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 上午11:56
 * @Version: 1.0
 **/
public class Test2HamcrestAssertions {

    @Test
    void assertWithHamcrestMatcher() {
        // 注意此处要使用hamcrest的assertThat（jupiter不提供），而在junit4中是使用junit提供的
        assertThat(2+1, is(equalTo(3)));
    }
}
