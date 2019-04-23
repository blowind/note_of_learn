package com.zxf.nio.client;

/**
 * @ClassName: AIOClient
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/23 0:20
 * @Version: 1.0
 **/
public class AIOClient {
    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {
                // 采用默认值
            }
        }

        new Thread(new AIOClientRunner("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
    }
}
