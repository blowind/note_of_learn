package com.zxf.elasticjob.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.executor.handler.impl.DefaultExecutorServiceHandler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.zxf.elasticjob.annotation.ElasticSimpleJob;
import com.zxf.elasticjob.listener.LogElasticJobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @ClassName: ElasticJobConfig
 * @Description:
 * Elastic-Job配置分为3个层级，分别是Core, Type和Root。每个层级使用相似于装饰者模式的方式装配。
 *
 * Core对应JobCoreConfiguration，用于提供作业核心配置信息，如：作业名称、分片总数、CRON表达式等。
 *
 * Type对应JobTypeConfiguration，有3个子类分别对应SIMPLE, DATAFLOW和SCRIPT类型作业，
 * 提供3种作业需要的不同配置，如：DATAFLOW类型是否流式处理或SCRIPT类型的命令行等。
 *
 * Root对应JobRootConfiguration，有2个子类分别对应Lite和Cloud部署类型，
 * 提供不同部署类型所需的配置，如：Lite类型的是否需要覆盖本地配置或Cloud占用CPU或Memory数量等。
 *
 * @Author: ZhangXuefeng
 * @Date: 2019/3/30 23:31
 * @Version: 1.0
 **/
@Configuration
public class ElasticJobConfig {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    @PostConstruct
    public void initAllElasticJob() {
        Map<String, SimpleJob> simpleMap = context.getBeansOfType(SimpleJob.class);

        for(Map.Entry<String, SimpleJob> entry : simpleMap.entrySet()) {
            SimpleJob bean = entry.getValue();
            if(!bean.getClass().isAnnotationPresent(ElasticSimpleJob.class)) {
                throw new RuntimeException(bean.getClass().getSimpleName() + " has no ElasticSimpleJob annotation !");
            }

            Class<?> clazz = bean.getClass();
            ElasticSimpleJob anno = clazz.getAnnotation(ElasticSimpleJob.class);
            JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(
                    clazz.getName(), anno.cron(), anno.shardingTotalCount())
                    .shardingItemParameters(anno.shardingItemParameters())
                    .jobParameter(anno.jobParameter())
                    .description(anno.description())
                    .build();

            JobTypeConfiguration typeConfig = new SimpleJobConfiguration(coreConfig,
                    clazz.getCanonicalName());

            LiteJobConfiguration liteConfig = LiteJobConfiguration.newBuilder(typeConfig)
                                                .overwrite(anno.overwrite())
                                                .build();

            SpringJobScheduler scheduler = new SpringJobScheduler(bean, registryCenter, liteConfig, new LogElasticJobListener());
            scheduler.init();
        }
    }
}
