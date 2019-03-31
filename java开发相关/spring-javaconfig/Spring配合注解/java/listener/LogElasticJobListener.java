package com.zxf.elasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: LogElasticJobListener
 * @Description:
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
