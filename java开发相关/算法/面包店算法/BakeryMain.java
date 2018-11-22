package com.zxf.bakery;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: BakeryMain
 * @Description: 面包店算法，不使用信号量而是用纯软件方法实现资源锁，解决多线程调度临界区资源
 *
 * 算法效果：
 * 1、对于申请临界区资源的进程，按照申请时间的先后得到获取资源的优先级（本质是先申请的获取较小的排号值）；
 * 2、对于同时申请资源的进程，按照PID大小进行优先级排序；
 * 3、对于使用完资源后立即申请的进程，由于排号值较大而排到所有之前申请的进程之后，避免了饥饿的问题
 *
 * @Author: ZhangXuefeng
 * @Date: 2018/11/22 10:46
 * @Version: 1.0
 **/
public class BakeryMain {
    private static AtomicInteger nextPID = new AtomicInteger(0);

    public static void main(String[] args) {
        for(int i = 0 ; i < BakeryLock.THREAD_NUMBER; i++) {
            /*所有进程具有唯一的PID是本算法的前置要求，由于PID的分配仅在启动时运行一次，所以有锁操作也不影响整体性能*/
            new MyThread(nextPID.getAndIncrement()).start();
        }
    }
}
