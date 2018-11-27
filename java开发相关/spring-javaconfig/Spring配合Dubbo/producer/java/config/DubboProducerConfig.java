package com.zxf.producer.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.SimpleTransactionStatus;

/**
 * @ClassName: DubboProducerConfig
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 19:08
 * @Version: 1.0
 **/
@Configuration
@DubboComponentScan("com.zxf.producer")  /*显示指定扫描Dubbo服务的注解*/
@EnableTransactionManagement
public class DubboProducerConfig {

    @Bean   /*配置当前应用*/
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("zxf-dubbo-provider");
        return applicationConfig;
    }

    /*
    可以使用Multicast、Redis、Zookeeper、Simple这四个作为注册中心

    multicast: 不需要启动任何中心节点，只要广播地址就可以互相返现
    zookeeper：使用zookeeper作为注册中心
    redis： 使用redis作为注册中心
    simple：普通的dubbo服务，减少第三方依赖
    */

    /*不使用注册中心，在消费者中使用@Reference指定url强行连接*/
    /*@Bean
    public RegistryConfig registryConfig() {
        *//*可以使用Multicast、Redis、Zookeeper、Simple这四个作为注册中心*//*
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("N/A");
        return registryConfig;
    }*/

    /*不使用注册中心，使用广播方式互相发现*/
    /*@Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("multicast://224.5.6.7:56789");  // 广播地址：scope: 224.0.0.0 - 239.255.255.255
        return registryConfig;
    }*/

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("redis://47.75.50.16:6379");
        registryConfig.setUsername("root");
        registryConfig.setPassword("1qaz2wsxHZ");
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(12345);
        return protocolConfig;
    }

    /*此处通过添加事务管理器的打印配合验证Dubbo Service的事务功能*/
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new PlatformTransactionManager() {
            public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {
                System.out.println("get transaction ...");
                return new SimpleTransactionStatus();
            }

            public void commit(TransactionStatus transactionStatus) throws TransactionException {
                System.out.println("commit transaction ...");
            }

            public void rollback(TransactionStatus transactionStatus) throws TransactionException {
                System.out.println("rollback transaction ...");
            }
        };
    }

}
