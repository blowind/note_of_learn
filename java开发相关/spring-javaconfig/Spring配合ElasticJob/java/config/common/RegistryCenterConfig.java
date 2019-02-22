package com.zxf.elasticjob.config.common;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  @ClassName: RegistryCenterConfig
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/2/22 14:43
 *  @Version: 1.0
 **/
@Configuration
@ConditionalOnExpression("'$regCenter.serverList}'.length() > 0")
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
