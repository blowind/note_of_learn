package com.zxf.zxfbatis.simple;

import com.zxf.zxfbatis.simple.mapper.CountryMapper;
import com.zxf.zxfbatis.simple.model.Country;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.util.List;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/15 17:13
 */
public class MyBatisMain {

	public static void main(String[] argv) {
		/*数据库连接池信息*/
		PooledDataSource dataSource = new PooledDataSource();
		dataSource.setDriver("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
		dataSource.setDefaultAutoCommit(false);
		/*采用MyBatis的JDBC事务*/
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, dataSource);

		/*创建Configuration对象*/
		Configuration configuration = new Configuration(environment);
		/*注册一个MyBatis上下文别名（非必须）*/
		configuration.getTypeAliasRegistry().registerAlias("Country", Country.class);
		/*指定映射器*/
		configuration.addMapper(CountryMapper.class);
		/*通过指定路径批量指定映射器*/
		// configuration.addMappers("com.zxf.zxfbatis.simple.mapper");


		/*具体用例*/
		SqlSession sqlSession = null;
		try{
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(configuration);
			sqlSession = factory.openSession();
			List<Country> ret = sqlSession.selectList("com.zxf.zxfbatis.simple.mapper.CountryMapper.selectAll");
			for(Country c : ret) {
				StringBuilder sb = new StringBuilder();
				sb.append("id: ").append(c.getId()).append(", name: ").append(c.getCountryname()).append(", code: ").append(c.getCountrycode());
				System.out.println(sb.toString());
			}

			CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
			Country ret2 = mapper.selectById2();
			System.out.println(ret2);


			sqlSession.commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			sqlSession.rollback();
		}finally {
			sqlSession.close();
		}

	}
}
