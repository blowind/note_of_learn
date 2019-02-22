package com.zxf.elasticjob.job.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  @ClassName: SpringSimpleJob
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/2/22 15:03
 *  @Version: 1.0
 **/
@Component
public class SpringSimpleJob implements SimpleJob {

	@Override
	public void execute(final ShardingContext shardingContext) {
		System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
											shardingContext.getShardingItem(),
											new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
											Thread.currentThread().getId(),
											"SIMPLE"));
	}
}
