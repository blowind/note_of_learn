package com.chuanying.bitin.admin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 时间线获取工具函数，根据当前时间获取当天、本周、本月、本年的开始和结束时间
 * 				 SimpleDateFormat是线程不安全的
 * @date 2018/08/29 09:30
 */
public class TimelineUtils {

	/* HH标记表示使用24小时制，hh表示使用12小时制 */
	/* private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); */
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static SimpleDateFormat DAY_START_FORMAT = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	private static SimpleDateFormat DAY_END_FORMAT = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

	/**
	 *  获取今天开始时间
	 */
	public static Date startOfToday() throws ParseException {
		return DATE_FORMAT.parse(startOfTodayInStr());
	}

	/**
	 * 获取今天结束时间
	 */
	public static Date endOfToday() throws ParseException {
		return DATE_FORMAT.parse(endOfTodayInStr());
	}

	/**
	 * 获取本周第一天（周一）开始时间
	 */
	public static Date startOfThisWeek() throws ParseException {
		return DATE_FORMAT.parse(startOfThisWeekInStr());
	}

	/**
	 * 获取本周最后一天（周日）结束时间
	 */
	public static Date endOfThisWeek() throws ParseException {
		return DATE_FORMAT.parse(endOfThisWeekInStr());
	}

	/**
	 * 获取本月1号开始时间
	 */
	public static Date startOfThisMonth() throws ParseException {
		return DATE_FORMAT.parse(startOfThisMonthInStr());
	}

	/**
	 * 获取本月最后一天结束时间
	 */
	public static Date endOfThisMonth() throws ParseException {
		return DATE_FORMAT.parse(endOfThisMonthInStr());
	}

	/**
	 * 获得今年1月1日开始时间
	 */
	public static Date startOfThisYear() throws ParseException {
		return DATE_FORMAT.parse(startOfThisYearInStr());
	}

	/**
	 * 获得今年12月31日结束时间
	 */
	public static Date endOfThisYear() throws ParseException {
		return DATE_FORMAT.parse(endOfThisYearInStr());
	}

	/**
	 *  获取今天开始时间（字符串）
	 */
	public static String startOfTodayInStr() {
		return DAY_START_FORMAT.format(new Date());
	}

	/**
	 * 获取今天结束时间（字符串）
	 */
	public static String endOfTodayInStr() {
		return DAY_END_FORMAT.format(new Date());
	}

	/**
	 * 获取本周第一天（周一）开始时间（字符串）
	 * 周期时间是从1到7的区间算，对应本周从前到后依次的周日-周六，超出这个范围的数值与5取模对应到前述区间
	 */
	public static String startOfThisWeekInStr() {
		Calendar cal = Calendar.getInstance();
		/*错误写法：因为每周从周日开始，在周日那天设置的话就变成下周的周一了
		cal.set(Calendar.DAY_OF_WEEK, 2);
		*/
		/*1表示周日，2表示周一，因此减去2，对于1的处理，此处好像有问题*/
		int weekday = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -weekday);
		return DAY_START_FORMAT.format(cal.getTime());
	}

	/**
	 * 获取本周最后一天（周日）结束时间（字符串）
	 */
	public static String endOfThisWeekInStr() {
		Calendar cal = Calendar.getInstance();
		/*错误写法：因为每周从周日开始，在周日那天设置的话就变成下周的周日了
		cal.set(Calendar.DAY_OF_WEEK, 7);
		cal.add(Calendar.DATE, 1);
		*/

		/*周日是1，在当前基础上加7天，可能有问题，其他日期没问题*/
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, 8 - weekday);
		return DAY_END_FORMAT.format(cal.getTime());
	}

	/**
	 * 获取本月1号开始时间（字符串）
	 */
	public static String startOfThisMonthInStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return DAY_START_FORMAT.format(cal.getTime());
	}

	/**
	 * 获取本月最后一天结束时间（字符串）
	 */
	public static String endOfThisMonthInStr() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return DAY_END_FORMAT.format(cal.getTime());
	}

	/**
	 * 获得今年1月1日开始时间（字符串）
	 */
	public static String startOfThisYearInStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);

		/* 第二种实现方式
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		*/
		return DAY_START_FORMAT.format(cal.getTime());
	}

	/**
	 * 获得今年12月31日结束时间
	 */
	public static String endOfThisYearInStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);

		/* 第二种实现方式
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DATE, 31);
		*/
		return DAY_END_FORMAT.format(cal.getTime());
	}

	/**
	 * 获得当前季度的开始时间（字符串）
	 */
	public static String startOfThisSeasonInStr() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if(month >= 1 && month <= 3) {
			cal.set(Calendar.MONTH, 0);
		}else if(month >= 4 && month <= 6) {
			cal.set(Calendar.MONTH, 3);
		}else if(month >= 7 && month <= 9) {
			cal.set(Calendar.MONTH, 6);
		}else if(month >= 10 && month <= 12) {
			cal.set(Calendar.MONTH, 9);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return DAY_START_FORMAT.format(cal.getTime());
	}

	/**
	 * 获得当前季度的结束时间（字符串）
	 */
	public static String endOfThisSeasonInStr() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if(month >= 1 && month <= 3) {
			cal.set(Calendar.MONTH, 2);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}else if(month >= 4 && month <= 6) {
			cal.set(Calendar.MONTH, 5);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}else if(month >= 7 && month <= 9) {
			cal.set(Calendar.MONTH, 8);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}else if(month >= 10 && month <= 12) {
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		return DAY_END_FORMAT.format(cal.getTime());
	}

	/**
	 * 获得上/下半年的开始时间（字符串）
	 */
	public static String startOfHalfYearInStr() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if(month >= 1 && month <= 6) {
			cal.set(Calendar.MONTH, 0);
		}else if(month >= 7 && month <= 12) {
			cal.set(Calendar.MONTH, 6);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return DAY_START_FORMAT.format(cal.getTime());
	}

	/**
	 * 获得上/下半年的结束时间（字符串）
	 */
	public static String endOfHalfYearInStr() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if(month >= 1 && month <= 6) {
			cal.set(Calendar.MONTH, 5);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}else if(month >= 7 && month <= 12) {
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		return DAY_END_FORMAT.format(cal.getTime());
	}

	/**
	 *  获取W3DTF格式的时间字符串，样例：2003-12-15T14:43:07Z
	 */
	public static String getFormatOfW3DTF() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(cal.getTime());
	}

	public static void main(String[] argv) {
		try{
			System.out.println(startOfTodayInStr());
			System.out.println(endOfTodayInStr());
			System.out.println(startOfThisWeekInStr());
			System.out.println(endOfThisWeekInStr());
			System.out.println(startOfThisMonthInStr());
			System.out.println(endOfThisMonthInStr());
			System.out.println(startOfThisYearInStr());
			System.out.println(endOfThisYearInStr());

			System.out.println("=========================");
			System.out.println(startOfToday());
			System.out.println(endOfToday());
			System.out.println(startOfThisWeek());
			System.out.println(endOfThisWeek());
			System.out.println(startOfThisMonth());
			System.out.println(endOfThisMonth());
			System.out.println(startOfThisYear());
			System.out.println(endOfThisYear());
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
