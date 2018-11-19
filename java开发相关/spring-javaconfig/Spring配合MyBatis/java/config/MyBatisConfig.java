package com.zxf.spring.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.zxf.spring.mybatis.dao.UserMapper;
import lombok.Setter;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import tk.mybatis.spring.annotation.MapperScan;
import tk.mybatis.spring.mapper.MapperFactoryBean;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName: MyBatisConfig
 * @Description: MyBatis配置类
 * @Author: ZhangXuefeng
 * @Date: 2018/11/19 23:36
 * @Version: 1.0
 **/
/*
最基本的使用时，通过注解@MapperScan加上application.properties文件就可简单使用
较复杂的使用时，通过注解@MapperScan加上application.properties文件指定mybatis-config.xml文件位置并在mybatis-config.xml文件中进行定制化配置；
一次性使用时，也可以通过注解@MapperScan加上下述Java代码配置的方式使用
*/
@Configuration
@MapperScan(basePackages = "com.zxf.spring.mybatis.dao", annotationClass = Repository.class)
@ConfigurationProperties("spring.datasource")
@Setter
public class MyBatisConfig {
    /*@Autowired
    private SqlSessionFactory sqlSessionFactory;*/

    /*装配加载所有的Mapper接口总共有三种方式：
    1、类MapperFactoryBean，针对一个个接口进行配置
    2、类MapperScannerConfigurer，通过Java代码配置扫描装配，批量配置
    3、注解@MapperScan，通过注解扫描装配，批量配置
    */

   /* @Bean
    public MapperFactoryBean<UserMapper> initUserMapper() {
        MapperFactoryBean<UserMapper> bean = new MapperFactoryBean<>();
        bean.setMapperInterface(UserMapper.class);
        bean.setSqlSessionFactory(sqlSessionFactory);
        return bean;
    }*/

/*    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        加载SqlSessionFactory，SpringBoot会自动生成相应Bean
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        定义扫描的包
        configurer.setBasePackage("com.zxf.spring.mybatis.*");
//        限定被标注@Repository的接口才被扫描
        configurer.setAnnotationClass(Repository.class);
//        限制继承某个接口的才被扫描，使用的不多
//        configurer.setMarkerInterface();
        return configurer;
    }*/

    /*以下使用Java代码配置形式而不是 mybatis-config.xml 方式生成MyBatis相关配置*/

    private String username;
    private String password;
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DruidDataSource DataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setUsername(username);
        source.setPassword(password);
        source.setUrl(url);
        source.setDriverClassName(driverClassName);
        return source;
    }

    @Bean
    public org.apache.ibatis.session.Configuration getConfiguration() {
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setCacheEnabled(false);
        config.setLazyLoadingEnabled(false);
        config.setMultipleResultSetsEnabled(true);
        config.setUseColumnLabel(true);
        config.setUseGeneratedKeys(true);
        config.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        config.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.WARNING);
        config.setDefaultExecutorType(ExecutorType.REUSE);
        config.setDefaultStatementTimeout(20000);
        config.setSafeRowBoundsEnabled(false);
        config.setMapUnderscoreToCamelCase(true);
        config.setLogImpl(StdOutImpl.class);
        config.setLocalCacheScope(LocalCacheScope.SESSION);
        config.setJdbcTypeForNull(JdbcType.OTHER);
        return config;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource druidDataSource, @Autowired org.apache.ibatis.session.Configuration config) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(druidDataSource);
        factoryBean.setConfiguration(config);
        factoryBean.setTypeHandlersPackage("com.zxf.spring.mybatis.handler");
        factoryBean.setTypeAliasesPackage("com.zxf.spring.mybatis.model");
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/zxf/spring/mybatis/mapper/*.xml"));
//        Interceptor[] plugins = {new PageInterceptor()};

        Interceptor pageHelper = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        pageHelper.setProperties(properties);
        Interceptor[] plugins = {pageHelper};
        factoryBean.setPlugins(plugins);
        return factoryBean;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DruidDataSource druidDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(druidDataSource);
        return dataSourceTransactionManager;
    }
}
