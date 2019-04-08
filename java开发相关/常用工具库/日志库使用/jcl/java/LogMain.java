package com.zxf.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: LogMain
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/5 12:36
 * @Version: 1.0
 **/
public class LogMain {
    public static void main(String[] args) {
        /* 使用common-logging日志接口开发则使用下面的类进行初始化 */
//        Log logger = LogFactory.getLog(LogMain.class);
        Logger logger = LoggerFactory.getLogger(LogMain.class);

        logger.debug("通过log4j或者jdk自带日志框架输出debug日志");
        logger.info("通过log4j或者jdk自带日志框架输出info日志，附带数字：{}", 100);
        logger.warn("通过log4j或者jdk自带日志框架输出warn日志，附带字符串：{}", "hello");
    }
}
