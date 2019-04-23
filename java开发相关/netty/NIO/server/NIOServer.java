package com.zxf.nio.server;

/**
 * @ClassName: NIOServer
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/12/30 12:03
 * @Version: 1.0
 **/
public class NIOServer {

    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {}
        }
        /* 启用一个独立的线程轮询多路复用器Selector，处理多个客户端的并发接入 */
        NIOServerRunner runner = new NIOServerRunner(port);
        new Thread(runner, "NIO-MultiplexerServer").start();
    }

}
