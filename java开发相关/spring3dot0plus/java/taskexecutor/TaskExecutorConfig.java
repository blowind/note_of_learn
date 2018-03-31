package com.zxf.taskexecutor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@ComponentScan("com.zxf.taskexecutor")
@EnableAsync   // 开启对异步任务的支持，否则任务都是同步跑的
public class TaskExecutorConfig implements AsyncConfigurer {
	@Override
	public Executor getAsyncExecutor() {
//		ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(5, 10, 60*1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		return taskExecutor;
	}

}
