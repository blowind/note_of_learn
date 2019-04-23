package com.zxf.nio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: FakeAIOServer
 * @Description: 伪异步IO，本质上是BIO的优化，只是通过线程池和消息队列防止资源爆炸
 * @Author: ZhangXuefeng
 * @Date: 2018/12/30 11:16
 * @Version: 1.0
 **/
public class FakeAIOServer {
    public static void main(String[] args) throws IOException{
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {}
        }
        ServerSocket server = null;
        try{
            server = new ServerSocket(port);
            System.out.println("The time server is start in port: " + port);
            Socket socket = null;
            FakeAioHandlerExecutePool singleExecutor = new FakeAioHandlerExecutePool(50, 10000);
            while (true) {
                socket = server.accept();
                singleExecutor.execute(new BIOServerHandler(socket));
            }
        }finally {
            if(server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
