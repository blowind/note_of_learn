package com.junit;

import static org.junit.Assert.*;

import com.junit.impl.Calculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 111
 * @date 2018/06/30 13:38
 */
@RunWith(value = Parameterized.class)
public class ParameterizedTest {
	private double expected;
	private double valueOne;
	private double valueTwo;

	@Parameterized.Parameters
	public static Collection<Integer[]> getTestParameters() {
		return Arrays.asList(new Integer[][] {
				{2, 1, 1},
				{3, 2, 1},
				{4, 3, 1},
		});
	}

	public ParameterizedTest(double expected, double valueOne, double valueTwo) {
		this.expected = expected;
		this.valueOne = valueOne;
		this.valueTwo = valueTwo;
	}

	@Test
	public void sum() {
		Calculator calc = new Calculator();
		assertEquals(expected, calc.add(valueOne, valueTwo), 0);
	}

}
