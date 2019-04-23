package com.zxf.nio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName: BIOClient
 * @Description: 基本的阻塞式IO（BIO）的客户端代码示例
 * @Author: ZhangXuefeng
 * @Date: 2018/12/29 23:21
 * @Version: 1.0
 **/
public class BIOClient {
    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed.");
            String resp = in.readLine();
            System.out.println("Now is : " + resp);
        }catch (Exception e) {
        }finally {
            if(out != null) {
                out.close();
                out = null;
            }
            if(in != null) {
                try{
                    in.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if(socket != null) {
                try{
                    socket.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
