package com.zxf;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *  @ClassName: ConcurrentTimelineUtils
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/4/12 15:04
 *  @Version: 1.0
 **/
public class ConcurrentTimelineUtils {

	public static final DateTimeFormatter STANDARD_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DAY_START_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
	public static final DateTimeFormatter DAY_END_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59");

	public static String startOfTodayStr() {
		return DAY_START_FORMAT.format(LocalDateTime.now());
	}

	public static String endOfTodayStr() {
		return DAY_END_FORMAT.format(LocalDateTime.now());
	}

	public static Date startOfToday() {
		LocalDateTime localDateTime = LocalDateTime.parse(startOfTodayStr(), STANDARD_FORMAT);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date endOfToday() {
		LocalDateTime localDateTime = LocalDateTime.parse(endOfTodayStr(), STANDARD_FORMAT);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date startOfNDaysBefore(long n) {
		LocalDateTime localDateTime = LocalDateTime.parse(startOfTodayStr(), STANDARD_FORMAT).minusDays(n);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date endOfNDaysBefore(long n) {
		LocalDateTime localDateTime = LocalDateTime.parse(endOfTodayStr(), STANDARD_FORMAT).minusDays(n);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date startOfNDaysLater(long n) {
		LocalDateTime localDateTime = LocalDateTime.parse(startOfTodayStr(), STANDARD_FORMAT).plusDays(n);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date endOfNDaysLater(long n) {
		LocalDateTime localDateTime = LocalDateTime.parse(endOfTodayStr(), STANDARD_FORMAT).plusDays(n);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static void main(String[] args) {
		System.out.println(startOfTodayStr());
		System.out.println(endOfTodayStr());

		System.out.println(startOfToday());
		System.out.println(endOfToday());

		System.out.println(startOfNDaysBefore(1));
		System.out.println(endOfNDaysBefore(1));

		System.out.println(startOfNDaysBefore(90));
		System.out.println(endOfNDaysBefore(90));

		System.out.println(startOfNDaysLater(90));
		System.out.println(endOfNDaysLater(90));
	}
}
