package com.zxf.elasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: LogElasticJobListener
 * @Description:
 *		配置：
 *		每个作业节点JOB对应的监听器，在作业开始前和结束后进行清理动作，一般是文件处理，资源打开之类操作
 *		此类型任务实现简单，且无需考虑全局分布式任务是否完成，请尽量使用此类型监听器
 *		注意一般是在单台服务器上部署一个本服务，每个Job都是单例的时候好用，不然存在多线程资源冲突的问题
 *		使用：
 *		JobScheduler中最后一个参数 ElasticJobListener... elasticJobListeners
 *
 * @Author: ZhangXuefeng
 * @Date: 2019/3/30 23:56
 * @Version: 1.0
 **/
@Slf4j
public class LogElasticJobListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts contexts) {
//        log.info("定时任务{}开始执行，分片参数为：{}， Job传入参数为"
//                , contexts.getJobName()
//                , contexts.getShardingItemParameters()
//                , contexts.getJobParameter());
    }

    @Override
    public void afterJobExecuted(ShardingContexts contexts) {
//        log.info("定时任务{}执行完毕，分片参数为：{}， Job传入参数为"
//                , contexts.getJobName()
//                , contexts.getShardingItemParameters()
//                , contexts.getJobParameter());
    }
}
