package com.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/07/06 10:19
 */
//@Disabled
public class DisabledDemo {

	@Disabled
	@Test
	void testWillBeSkipped() {
		System.out.println("skipped test");
	}

	@Test
	void testWillBeExecuted() {
		System.out.println("running one");
	}
}
