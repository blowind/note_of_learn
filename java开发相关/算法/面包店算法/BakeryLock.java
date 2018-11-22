package com.zxf.bakery;

import java.util.Arrays;

public class BakeryLock {
    /*本锁支持的最大线程数*/
    public static final int THREAD_NUMBER = 10;
    /*标记当前进程是否在取号，取号为true，初始默认值为false*/
    private static volatile boolean[] choosing = new boolean[THREAD_NUMBER];
    /*数组下标对应的进程取到的号值*/
    private static volatile int[] numbers = new int[THREAD_NUMBER];

    /**
     * 先比号值，值小的优先级高，在号值相等的时候，比较PID值，值小的优先级高
     * @param x1 第一个二元组的号值
     * @param x2 第一个二元组的PID值
     * @param y1 第二个二元组的号值
     * @param y2 第二个二元组的PID值
     * @return true当二元组y没有更高优先级时
     *          false当二元组y有更高优先级时
     */
    public static boolean isLeftTupleRunFirst(int x1, int x2, int y1, int y2) {
        return (x1 < y1) || ((x1 == y1) && (x2 < y2));
    }

    /**
     * 获取资源锁的操作，并非使用常规的信号量实现
     * @param pid 当前获取资源锁的进程pid
     */
    public static void lock(int pid) {
        try{
            /*所有需要使用临界区资源的进程，都要先取号，号的值取当前已有号的最大值加1，
              因为不在意两个进程取到同一个号值，因此不需要加锁*/
            choosing[pid] = true;
            numbers[pid] = 1 + Arrays.stream(numbers).max().getAsInt();
            choosing[pid] = false;

            for(int j = 0; j < THREAD_NUMBER; j++) {
                /*防止当前正在取号的进程被拿出来判断，因为这个时候进程的号值会有波动*/
                while(choosing[j]);
                /*对所有已取号待未消耗的进程，选取号最好的进程跳出循环获得锁，对于号值一样的进程，按照PID小的原则选取*/
                /*所有进程同时进入到此处时：
                    1、所有号值不是最小的进程，在从0开始第一个遇到的小于本号值的进程j处阻塞；
                    2、所有号值最小的进程，在从0开始第一个遇到的小于本进程PID处阻塞；
                    3、号值最小且PID最小的进程，走完for循环后跳出，获得锁资源；
                    4、释放资源锁的进程立马取号，该号值也比之前所有取过号的进程号值大，避免饥饿问题；
                    5、释放资源锁后，当前号值最小且PID最小的进程可以继续走完for循环，其他同号值进程至少停在该进程处循环*/
                while(numbers[j] != 0 && isLeftTupleRunFirst(numbers[j], j, numbers[pid], pid))
                    /*此处sleep主要是为了防止等待循环长期占用CPU导致资源浪费，并非算法强制要求*/
                    Thread.sleep(10);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("===================pid: " + pid);
        }

    }
    /**
     * 释放资源锁，让其他等待在第二个while的进程可以获取锁
     * @param pid 当前释放资源所的进程pid
     */
    public static void unlock(int pid) {
        numbers[pid] = 0;
    }
}
