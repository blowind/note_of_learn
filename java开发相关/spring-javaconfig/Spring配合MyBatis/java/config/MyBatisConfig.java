package com.zxf.mybatis.generator.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Setter;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;
import com.github.pagehelper.PageInterceptor;

import java.io.IOException;
import java.util.Properties;


/**
 * @ClassName: MyBatisConfig
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/16 13:37
 * @Version: 1.0
 **/
@Configuration
@ConfigurationProperties("druid.pool")
@Setter
@MapperScan("com.zxf.mybatis.generator.mapper")
public class MyBatisConfig {

    private String username;
    private String password;
    private String jdbcUrl;
    private String driverClassName;

    @Bean
    public DruidDataSource DataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setUsername(username);
        source.setPassword(password);
        source.setUrl(jdbcUrl);
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
        factoryBean.setTypeAliasesPackage("com.zxf.**.model");
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/zxf/mybatis/generator/mapper/*.xml"));
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

//    public void print() {
//        System.out.println(username);
//        System.out.println(password);
//        System.out.println(jdbcUrl);
//        System.out.println(driverClassName);
//    }
}
