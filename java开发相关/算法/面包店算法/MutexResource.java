package com.zxf.bakery;

/**
 * @ClassName: MutexResource
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/22 11:15
 * @Version: 1.0
 **/
public class MutexResource {

    public static void useResource(int pid) throws InterruptedException {
        System.out.println("进程[" + pid + "]开始使用临界区（独占资源）");
        for(int i = 0; i < 3; i++) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        System.out.println("进程[" + pid + "]结束使用临界区（独占资源）");
    }
}
