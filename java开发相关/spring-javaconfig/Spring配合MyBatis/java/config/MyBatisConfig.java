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
 * @Description: MyBatis������
 * @Author: ZhangXuefeng
 * @Date: 2018/11/19 23:36
 * @Version: 1.0
 **/
/*
�������ʹ��ʱ��ͨ��ע��@MapperScan����application.properties�ļ��Ϳɼ�ʹ��
�ϸ��ӵ�ʹ��ʱ��ͨ��ע��@MapperScan����application.properties�ļ�ָ��mybatis-config.xml�ļ�λ�ò���mybatis-config.xml�ļ��н��ж��ƻ����ã�
һ����ʹ��ʱ��Ҳ����ͨ��ע��@MapperScan��������Java�������õķ�ʽʹ��
*/
@Configuration
@MapperScan(basePackages = "com.zxf.spring.mybatis.dao", annotationClass = Repository.class)
@ConfigurationProperties("spring.datasource")
@Setter
public class MyBatisConfig {
    /*@Autowired
    private SqlSessionFactory sqlSessionFactory;*/

    /*װ��������е�Mapper�ӿ��ܹ������ַ�ʽ��
    1����MapperFactoryBean�����һ�����ӿڽ�������
    2����MapperScannerConfigurer��ͨ��Java��������ɨ��װ�䣬��������
    3��ע��@MapperScan��ͨ��ע��ɨ��װ�䣬��������
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
//        ����SqlSessionFactory��SpringBoot���Զ�������ӦBean
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        ����ɨ��İ�
        configurer.setBasePackage("com.zxf.spring.mybatis.*");
//        �޶�����ע@Repository�Ľӿڲű�ɨ��
        configurer.setAnnotationClass(Repository.class);
//        ���Ƽ̳�ĳ���ӿڵĲű�ɨ�裬ʹ�õĲ���
//        configurer.setMarkerInterface();
        return configurer;
    }*/

    /*����ʹ��Java����������ʽ������ mybatis-config.xml ��ʽ����MyBatis�������*/

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
