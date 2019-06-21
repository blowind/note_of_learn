package com.simple.jupiter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @ClassName: Test8RandomParameters
 * @Description:
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 下午10:10
 * @Version: 1.0
 **/

@ExtendWith(Test8RandomParametersExtension.class)
public class Test8RandomParameters {

    @Test
    void injectInteger(@Test8RandomParametersExtension.Random int i,
                       @Test8RandomParametersExtension.Random int j) {
        assertNotEquals(i, j);
    }

    @Test
    void injectDouble(@Test8RandomParametersExtension.Random double d) {
        assertEquals(0.0, d, 1.0);
    }
}
