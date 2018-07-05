package com.junit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 11
 * @date 2018/06/30 14:36
 */
public class TestCaseA {
	@Test
	public void testA1() {
		assertEquals("Dummy test-case", 1+1, 2);
	}
}
