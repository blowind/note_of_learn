package com.zxf.nio.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: FakeAioHandlerExecutePool
 * @Description: 伪异步IO的线程池，使用jar包中ThreadPoolExecutor已有的队列和线程池
 * @Author: ZhangXuefeng
 * @Date: 2018/12/30 11:28
 * @Version: 1.0
 **/
public class FakeAioHandlerExecutePool {
    private ExecutorService executor;

    public FakeAioHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                                        maxPoolSize,
                                        120L,
                                        TimeUnit.SECONDS,
                                        new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }
}
