package com.zxf.taskexecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/***
	通过调用框架的调度线程池配置，所有的业务bean只需要关心如下三点：
	1、具体业务的异步执行方法实现；
	2、所有的异步方法要支持多线程重入，即本地不保存任何状态；
	3、所有异步方法被调用地点
	具体的生成线程，调度执行，维护线程池的动作都由后台线程池负责
***/

@Service  /*  @Async 如果注解在类级别，表明该类所有的方法都是异步方法 */
public class AsyncTaskService {

	/* 这里的方法自动被注入使用配置文件里返回的ThreadPoolTaskExecutor 作为 TaskExecutor */

	@Async /* 使用 @Async 该注解来声明本方法是个异步方法，多线程可重入 */
	public void executeAsyncTask(Integer i) {
		System.out.println("执行异步任务: " + i);
	}

	@Async
	public void executeAsyncTaskPlus(Integer i) {
		System.out.println("执行异步任务+1: " + (i+1));
	}
}
