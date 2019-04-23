package com.zxf.nio.client;

/**
 * @ClassName: NIOClient
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/1/3 22:30
 * @Version: 1.0
 **/
public class NIOClient {
    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {
                // 采用默认值
            }
        }

        new Thread(new NIOClientRunner("127.0.0.1", port), "TimeClient-001").start();
    }
}
