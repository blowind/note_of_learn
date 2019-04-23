package com.zxf.nio.server;

import java.net.ServerSocket;
import java.net.Socket;


/**
 * @ClassName: BIOServer
 * @Description: 基本的阻塞式IO（BIO）的客户端代码示例
 * @Author: ZhangXuefeng
 * @Date: 2018/12/29 23:30
 * @Version: 1.0
 **/
public class BIOServer {
    public static void main(String[] args) throws Exception{
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {
            }
        }
        ServerSocket server = null;
        try{
            server = new ServerSocket(port);
            System.out.println("The time server is start in port: " + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                new Thread(new BIOServerHandler(socket)).start();
            }
        }catch (Exception e) {}
        finally {
            if(server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }

}
