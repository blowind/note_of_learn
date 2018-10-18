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


		/*  对于同样的配置，
		在java程序中配置的属性优先级最高(能覆盖其他两种配置的值)，
		properties文件中优先级次之，
		xml文件中properties标签中的优先级最低  */


		/****************************************************************/
		/*通过java代码中进行配置的情况，不推荐，因为每次修改配置要改代码*/
		/****************************************************************/
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

			/* ibatis老版本遗留的使用方式，需要指定SQL id */
			List<Country> ret = sqlSession.selectList("com.zxf.zxfbatis.simple.mapper.CountryMapper.selectAll");
			for(Country c : ret) {
				StringBuilder sb = new StringBuilder();
				sb.append("id: ").append(c.getId()).append(", name: ").append(c.getCountryname()).append(", code: ").append(c.getCountrycode());
				System.out.println(sb.toString());
			}

			/* 新版本推荐的使用方式，完全的面向对象的使用方式，并且IDE能进行错误校验，和spring配合更好 */
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

		/*************************************************/
		/*通过xml和properties资源配置文件配置并加载的情况*/
		/*************************************************/
		try{
			/*加载properties文件中的属性值*/
			InputStream propertiesStream = Resources.getResourceAsStream("jdbc.properties");
			Properties props = new Properties();
			props.load(propertiesStream);
			/*取出密文用户名和密码，解密成明文后放回*/
			String username = props.getProperty("database.username");
			String password = props.getProperty("database.password");
			props.put("database.username", CodeUtils.decode(username));
			props.put("database.password", CodeUtils.decode(password));
			InputStream xmlStream = Resources.getResourceAsStream("mybatis-config.xml");
			/*加载xml配置和properties配置*/
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(xmlStream, props);
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
}
