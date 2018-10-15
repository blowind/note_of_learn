package com.zxf.zxfbatis.simple.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/12 13:57
 */
public class DateIndicator {
	private static Map<Integer, Integer> daysPerMonth = new HashMap<Integer, Integer>()
	{
		{
			put(1, 31);
			put(2, 28);
			put(3, 31);
			put(4, 30);
			put(5, 31);
			put(6, 30);
			put(7, 31);
			put(8, 31);
			put(9, 30);

		}
	};

	public static String getDayIndexOfYear() {
		Calendar c = Calendar.getInstance();
		int i = c.get(Calendar.DAY_OF_YEAR);
		return String.format("%03d", i);
	}

	public static void main(String[] argv) {
		System.out.println(DateIndicator.getDayIndexOfYear());

		int sum = 0;
		for(Map.Entry<Integer, Integer> entry : daysPerMonth.entrySet()) {
			sum += entry.getValue();
		}

		sum += 12;
		System.out.println(sum);
	}
}
