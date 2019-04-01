package com.zxf.annotationjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;

/**
 *  @ClassName: LogDistributeOneElasticJobListener
 *  @Description:
 *      分布式场景中仅单一节点执行的监听
 *      若作业处理数据库数据，处理完成后只需一个节点完成数据清理任务即可。
 *      此类型任务处理复杂，需同步分布式环境下作业的状态同步，提供了超时设置来避免作业不同步导致的死锁，请谨慎使用
 *
 *      使用：
 *  *	JobScheduler中最后一个参数 ElasticJobListener... elasticJobListeners
 *      会在内部协商并确定最后执行的节点
 *
 *  @Author: ZhangXuefeng
 *  @Date: 2019/4/1 10:55
 *  @Version: 1.0
 **/
public class LogDistributeOneElasticJobListener extends AbstractDistributeOnceElasticJobListener {

	/**
	 * @param startTimeoutMills   所有job服务启动的超时时间，超时后抛JobSystemException异常
	 * @param completeTimeoutMills  所有job服务运行的超时时间，超时后抛JobSystemException异常
	 */
	public LogDistributeOneElasticJobListener(long startTimeoutMills, long completeTimeoutMills) {
		super(startTimeoutMills, completeTimeoutMills);
	}

	@Override
	public void doBeforeJobExecutedAtLastStarted(ShardingContexts contexts) {
		// TO-DO
	}

	@Override
	public void doAfterJobExecutedAtLastCompleted(ShardingContexts contexts) {

	}
}
