package com.zxf.elasticjob.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zxf.elasticjob.annotation.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: SimpleJobDemo
 * @Description:
 * 查看zookeeper上存活的服务节点的命令：
 *  ./zkCli.sh -server 127.0.0.1:2181
 *  ls /my-elastic-job/com.zxf.annotationjob.jobs.SimpleJobDemo/instances
 *  路径一般是：
 *  ls /[namespace]/[classPath]/instances
 *
 * @Author: ZhangXuefeng
 * @Date: 2019/3/31 0:08
 * @Version: 1.0
 **/
@Slf4j
@ElasticSimpleJob(cron = "0/10 * * * * ?", shardingTotalCount = 5, shardingItemParameters = "0=Beijing,1=Shanghai,2=Guangzhou", jobParameter = "123456789")
public class SimpleJobDemo implements SimpleJob {
    @Override
    public void execute(final ShardingContext shardingContext) {
        log.info("TaskId: {} | Item: {} | Time: {}"
                , shardingContext.getTaskId()
                , shardingContext.getShardingItem()
                , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        log.info("JobName: {} | Thread: {} | jobParameter: {}"
//                , shardingContext.getJobName()
//                , Thread.currentThread().getId()
//                , shardingContext.getJobParameter());
//        log.info("===========================================");
    }
}
