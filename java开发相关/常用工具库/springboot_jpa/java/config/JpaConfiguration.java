package com.zxf.bootdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@EnableJpaRepositories("com.zxf.bootdata")
public class JpaConfiguration {
	
	/******  使用application.properties配置默认DataSource参数后，本文件内容可以为空，全部使用默认值  ******/
	
	
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder().setType(H2).build();
//        }
//    }

	@Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hjva = new HibernateJpaVendorAdapter();
        hjva.setShowSql(false);
        hjva.setGenerateDdl(true);
        hjva.setDatabase(Database.MYSQL);
//      hjva.setDatabase(Database.H2);
        return hjva;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        // 此处扫描的包已经要包括dao和model，不然会报Not a managed type异常
        lef.setPackagesToScan("com.zxf.bootdata");
        return lef;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		/*  JPA数据访问技术  */
        return new JpaTransactionManager(entityManagerFactory);
		
		// return new DataSourceTransactionManager(entityManagerFactory);  //  JDBC数据访问技术
		// return new HibernateTransactionManager(entityManagerFactory);       //  Hibernate数据访问技术
		// return new JdoTransactionManager(entityManagerFactory);             //  JDO数据访问技术
		// return new JtaTransactionManager(entityManagerFactory);             //  分布式事务数据访问技术
    }
}
