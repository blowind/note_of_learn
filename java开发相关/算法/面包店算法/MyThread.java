package com.zxf.bakery;

/**
 * @ClassName: MyThread
 * @Description: 每个单独的进程，根据PID进行区分
 * @Author: ZhangXuefeng
 * @Date: 2018/11/22 11:22
 * @Version: 1.0
 **/
public class MyThread extends Thread {
    private int pid;

    MyThread(int pid) {
        this.pid = pid;
    }

    @Override
    public void run() {
        try{
            System.out.println("进入线程，PID[" + pid + "]，等待10秒");
            Thread.sleep(5000);
            BakeryLock.lock(pid);
            MutexResource.useResource(pid);
            BakeryLock.unlock(pid);
        }catch (InterruptedException e) {
            System.out.println("线程[" + pid + "]故障，错误信息是:" + e.getMessage());
        }
    }

}
