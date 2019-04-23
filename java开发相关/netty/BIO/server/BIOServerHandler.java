package com.zxf.nio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @ClassName: BIOServerHandler
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/12/29 23:46
 * @Version: 1.0
 **/
public class BIOServerHandler implements Runnable {
    private Socket socket;

    public BIOServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while(true) {
                body = in.readLine();
                if(body == null) break;
                System.out.println("The time server receive order: " + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                        new Date().toString() : "Bad order";
                out.println(currentTime);
            }
        }catch (Exception e) {
            if(in != null) {
                try{
                    in.close();
                }catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if(out != null) {
                out.close();
                out = null;
            }
            if(this.socket != null) {
                try{
                    this.socket.close();
                }catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
