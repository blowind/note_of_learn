package com.zxf.nio.client;

/**
 * @ClassName: FakeAIOClient
 * @Description: 伪异步IO，本质上是BIO的优化，只是通过线程池和消息队列防止资源爆炸
				此处客户端代码与BIO的客户端代码一模一样
 * @Author: ZhangXuefeng
 * @Date: 2018/12/30 11:14
 * @Version: 1.0
 **/
public class FakeAIOClient {
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
