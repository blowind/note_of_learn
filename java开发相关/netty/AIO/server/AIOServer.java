package com.zxf.nio.server;

/**
 * @ClassName: AIOServer
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/22 23:31
 * @Version: 1.0
 **/
public class AIOServer {
    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {

            }
        }
        AIOServerRunner serverRunner = new AIOServerRunner(port);
        new Thread(serverRunner, "AIO-AsyncTimeServerHandler-001").start();
    }
}
