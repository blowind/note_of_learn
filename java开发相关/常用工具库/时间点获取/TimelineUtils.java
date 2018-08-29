package com.chuanying.bitin.admin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 时间线获取工具函数，根据当前时间获取当天、本周、本月、本年的开始和结束时间
 * @date 2018/08/29 09:30
 */
public class TimelineUtils {

	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static SimpleDateFormat DAY_START_FORMAT = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	private static SimpleDateFormat DAY_END_FORMAT = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

	public static Date startOfToday() throws ParseException {
		return DATE_FORMAT.parse(startOfTodayInStr());
	}

	public static Date endOfToday() throws ParseException {
		return DATE_FORMAT.parse(endOfTodayInStr());
	}

	public static Date startOfThisWeek() throws ParseException {
		return DATE_FORMAT.parse(startOfThisWeekInStr());
	}

	public static Date endOfThisWeek() throws ParseException {
		return DATE_FORMAT.parse(endOfThisWeekInStr());
	}

	public static Date startOfThisMonth() throws ParseException {
		return DATE_FORMAT.parse(startOfThisMonthInStr());
	}

	public static Date endOfThisMonth() throws ParseException {
		return DATE_FORMAT.parse(endOfThisMonthInStr());
	}

	public static Date startOfThisYear() throws ParseException {
		return DATE_FORMAT.parse(startOfThisYearInStr());
	}

	public static Date endOfThisYear() throws ParseException {
		return DATE_FORMAT.parse(endOfThisYearInStr());
	}

	public static String startOfTodayInStr() {
		return DAY_START_FORMAT.format(new Date());
	}

	public static String endOfTodayInStr() {
		return DAY_END_FORMAT.format(new Date());
	}

	public static String startOfThisWeekInStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, 2);
		return DAY_START_FORMAT.format(cal.getTime());
	}

	public static String endOfThisWeekInStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, 7);
		cal.add(Calendar.DATE, 1);
		return DAY_END_FORMAT.format(cal.getTime());
	}

	public static String startOfThisMonthInStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return DAY_START_FORMAT.format(cal.getTime());
	}

	public static String endOfThisMonthInStr() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return DAY_END_FORMAT.format(cal.getTime());
	}

	public static String startOfThisYearInStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return DAY_START_FORMAT.format(cal.getTime());
	}

	public static String endOfThisYearInStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return DAY_END_FORMAT.format(cal.getTime());
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
