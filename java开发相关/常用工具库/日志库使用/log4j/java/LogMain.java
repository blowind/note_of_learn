package com.zxf.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  @ClassName: LogMain
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/4/4 11:18
 *  @Version: 1.0
 **/
public class LogMain {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(LogMain.class);

		logger.debug("通过log4j框架输出debug日志");
		logger.info("通过log4j框架输出info日志，附带数字：{}", 100);
		logger.warn("通过log4j框架输出warn日志，附带字符串：{}", "hello");
	}
}
