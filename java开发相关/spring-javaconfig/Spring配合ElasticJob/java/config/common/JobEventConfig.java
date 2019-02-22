package com.zxf.elasticjob.config.common;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *  @ClassName: JobEventConfig
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/2/22 13:22
 *  @Version: 1.0
 **/
@Configuration
public class JobEventConfig {

	@Resource
	private DataSource dataSource;

	@Bean
	public JobEventConfiguration jobEventConfiguration() {
		return new JobEventRdbConfiguration(dataSource);
	}
}
