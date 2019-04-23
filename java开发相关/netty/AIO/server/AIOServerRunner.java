package com.zxf.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: AIOServerRunner
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/22 23:33
 * @Version: 1.0
 **/
public class AIOServerRunner implements Runnable{
    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AIOServerRunner(int port) {
        this.port = port;
        try{
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port: " + port);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try{
            /* 使用闭锁让线程一直在此阻塞，防止服务端退出，类似while死循环的效果  */
            latch.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept() {
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
