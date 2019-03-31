package com.zxf.elasticjob.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RegistryCenter
 * @Description: Zookeeper注册配置
 * @Author: ZhangXuefeng
 * @Date: 2019/3/30 23:23
 * @Version: 1.0
 **/
@Configuration
public class RegistryCenterConfig {
    @Value("${regCenter.zookeeperList}")
    private String zookeeperList;
    @Value("${regCenter.namespace}")
    private String namespace;

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter() {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(zookeeperList, namespace));
    }
}
