package com.zxf.elasticjob.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @AnnotationName: ElasticSimpleJob
 * @Description:
 *
 * Elastic-Job实现分布式作业幂等性的配置未添加，原则上要请定时任务开发者自己保证分布式作业幂等性
 * 所谓幂等性是：每一个任务不论在哪台服务器执行，不论执行多少次，
 *               每次执行结果都是当下最正确的并且不产生其他副作用
 * 若执行的任务有幂等性要求，请使用消息中间件（kafka）或者版本控制（版本号）自行实现
 * 本服务原则上仅提供两个功能：1.任务定时调度  2.任务均匀分片
 *
 * 同一台作业服务器可以运行多个相同的作业实例，但每个作业实例必须使用不同的JobInstanceId，
 * 因为作业运行时是按照IP和JobInstanceId注册和管理的。
 * 本服务设计原则上要求一个任务对应一个Class，每个Class作为单例Bean运行，
 * 不存在一个服务运行多个相同作业实例的情况，如需相关设计请自行处理JobInstanceId配置
 *
 * @Author: ZhangXuefeng
 * @Date: 2019/3/30 23:31
 * @Version: 1.0
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ElasticSimpleJob {
	/**
	 * cron表达式，用于控制作业触发时间
	 * 示例："0/10 * * * * ?"   10秒钟触发一次任务执行
	 */
	String cron();

    /**
     * 分片数，结合运行任务的具体需求设计分片数
     * 当任务可以设计成多个分片并行运行时，建议设置为大于服务器的数量，最好是大于服务器倍数的数量
     * 例如：部署3个服务，设置分片数为10时，大概按照3,3,4的分配方式给3个程序分配分片
     *
     * 分片数与服务部署没有关联性，每个服务分配到的分片 ≈ 分片数 / 服务部署数目
     *
     * 当服务部署数目大于分片数时，按照主备模式运行任务
     * 例如：部署3个服务包，设置分片数为1时，按照1主2备的情况运行，
     * 正常情况下任务跑在主服务上，若主服务崩溃，备服务会在下次作业启动时替补执行
     * 若开启失效转移功能可以保证主服务崩溃时备机立即启动替补执行
     */
    int shardingTotalCount();

	/**
	 * 分片参数，逗号分隔的多个key=value格式组成，
	 * 其中key作为分片的区分参数，必须是int类型，value多用于助记字符串，也可以当做枚举类型的匹配项
	 * 分片参数示例： "0=A,1=B,2=C"
	 * 其中0,1,3是key，A,B,C是value，
	 * 调度函数execute()入参shardingContext通过getShardingItem()获取的都是key
	 *
	 * 实测这个配置好像不生效，永远按照[0, count-1]进行分片
	 */
	String shardingItemParameters();

	/**
	 * 传给调度任务的自定义参数
	 * 通过调度函数execute()入参shardingContext获取
	 * 可以放置诸如：每次获取的数据量，任务从数据库读取的主键等配置信息
	 */
	String jobParameter() default "";

	/**
	 * 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
	 */
	boolean failover() default false;

	/**
	 * 是否开启错过任务重新执行功能
	 */
	boolean misfire() default true;

	String description() default "SimpleJob";

	/**
	 * 用于运行环境下dump Job信息的端口，用户自己保证端口可用性
	 */
	int monitorPort() default -1;

	/**
	 * overwrite=true即允许客户端配置覆盖注册中心，反之则不允许（此处部署的服务即客户端）
	 * 若每个客户端的配置不一致，不做控制的话，最后一个启动的客户端配置将会成为注册中心的最终配置
	 * 如果注册中心无相关作业的配置，则无论overwrite是否配置，客户端配置都将写入注册中心
	 *
	 */
	boolean overwrite() default true;

	/**
	 * 是否将每个任务的启动和结束及运行结果记录到数据库
	 * 会在DataSource指定的数据库中创建两张表job_execution_log和job_status_trace_log
	 * 分别记录任务的启停状态和执行结果，执行状态和任务变化信息
	 * 用UUID做主键，在MySQL下IO性能不好
	 *
	 * 频繁执行的短任务不友好，日志很容易刷爆数据库
	 */
	boolean logJobStatusInDB() default false;
}
