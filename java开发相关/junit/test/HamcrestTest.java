package com.junit;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/07/02 09:27
 */
public class HamcrestTest {
	private List<String> values;

	@Before
	public void setUpList() {
		values = new ArrayList<String>();
		values.add("x");
		values.add("y");
		values.add("z");
	}

//	@Test
//	public void testWithoutHamcrest() {
//		assertTrue(values.contains("one") || values.contains("two")
//				|| values.contains("three"));
//	}
//
//	@Test
//	public void testWithHamcrest() {
//		assertThat(values, hasItem(anyOf(equalTo("one"), equalTo("two"),
//				equalTo("three"))));
//	}
}
