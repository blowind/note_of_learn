package com.junit;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class HamcrestAssertionDemo {
    @Test
    void assertWithHamcrestMatcher() {
        // 注意此处要使用hamcrest的assertThat，而在junit4中是使用junit提供的
        assertThat(2+1, is(equalTo(3)));
    }
}
