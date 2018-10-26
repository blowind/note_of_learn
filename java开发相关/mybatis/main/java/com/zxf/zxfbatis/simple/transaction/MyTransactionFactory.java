package com.zxf.zxfbatis.simple.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description  实现mybatis自定义的事务工厂
 * @date 2018/10/26 15:16
 */
public class MyTransactionFactory implements TransactionFactory {

	@Override
	public void setProperties(Properties props) {}

	@Override
	public Transaction newTransaction(Connection conn) {
		return new MyTransaction(conn);
	}

	@Override
	public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
		return new MyTransaction(dataSource, level, autoCommit);
	}
}
