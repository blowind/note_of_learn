package com.zxf.zxfbatis.simple.provider;

import org.apache.ibatis.mapping.DatabaseIdProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description  用于多数据库共存时选择
 * @date 2018/10/26 17:37
 */
public class MyDatabaseIdProvider implements DatabaseIdProvider {

	private static final String DATABASE_TYPE_DB2 = "DB2";
	private static final String DATABASE_TYPE_MYSQL = "MySQL";
	private static final String DATABASE_TYPE_ORACLE = "Oralce";

	@Override
	public void setProperties(Properties props) {
		System.out.println(props);
	}

	@Override
	public String getDatabaseId(DataSource dataSource) throws SQLException {
		Connection conn = dataSource.getConnection();
		String dbProductName = conn.getMetaData().getDatabaseProductName();
		if(MyDatabaseIdProvider.DATABASE_TYPE_DB2.equals(dbProductName)) {
			return "db2";
		}else if(MyDatabaseIdProvider.DATABASE_TYPE_MYSQL.equals(dbProductName)) {
			return "mysql";
		}else if(MyDatabaseIdProvider.DATABASE_TYPE_ORACLE.equals(dbProductName)){
			return "oracle";
		}else {
			return null;
		}
	}
}
