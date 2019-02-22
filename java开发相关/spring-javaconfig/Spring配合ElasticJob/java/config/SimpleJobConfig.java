package com.zxf.elasticjob.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 *  @ClassName: SimpleJobConfig
 *  @Description: 简单定时任务配置
 *  @Author: ZhangXuefeng
 *  @Date: 2019/2/22 13:21
 *  @Version: 1.0
 **/
@Configuration
public class SimpleJobConfig {
	@Value("${simpleJobOne.cron}")
	private String cron;
	@Value("${simpleJobOne.shardingTotalCount}")
	private int shardingTotalCount;
	@Value("${simpleJobOne.shardingItemParameters}")
	private String shardingItemParameters;

	@Resource
	private ZookeeperRegistryCenter registryCenter;
	@Resource
	private JobEventConfiguration jobEventConfiguration;

	@Bean(initMethod = "init")
	public JobScheduler simpleJobScheduler(@Autowired SimpleJob simpleJob) {
		JobCoreConfiguration coreConfig =
				JobCoreConfiguration.newBuilder(simpleJob.getClass().getName(), cron, shardingTotalCount)
									.shardingItemParameters(shardingItemParameters).build();

		JobTypeConfiguration typeConfig =
				new SimpleJobConfiguration(coreConfig, simpleJob.getClass().getCanonicalName());

		LiteJobConfiguration liteConfig = LiteJobConfiguration.newBuilder(typeConfig)
												.overwrite(true).build();

		return new SpringJobScheduler(simpleJob, registryCenter, liteConfig);

	}
}
