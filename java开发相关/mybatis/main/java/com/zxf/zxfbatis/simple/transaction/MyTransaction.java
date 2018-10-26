package com.zxf.zxfbatis.simple.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/26 15:20
 */
public class MyTransaction extends JdbcTransaction implements Transaction {

	public MyTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit) {
		super(ds, level, autoCommit);
	}

	public MyTransaction(Connection conn) {
		super(conn);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return super.getConnection();
	}

	@Override
	public void commit() throws SQLException {
		super.commit();
	}

	@Override
	public void rollback() throws SQLException {
		super.rollback();
	}

	@Override
	public void close() throws SQLException {
		super.close();
	}

	@Override
	public Integer getTimeout() throws SQLException {
		return super.getTimeout();
	}
}
