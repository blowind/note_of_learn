package com.zxf.nio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: AIOClientRunner
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/23 0:21
 * @Version: 1.0
 **/
public class AIOClientRunner implements CompletionHandler<Void, AIOClientRunner>, Runnable{
    private AsynchronousSocketChannel client;
    private String host;
    private int port;
    private CountDownLatch latch;

    public AIOClientRunner(String host, int port) {
        this.host = host;
        this.port = port;
        try{
            client = AsynchronousSocketChannel.open();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
		/* 异步连接， */
		/* 第一个this用于回调通知时作为入参被传递，第二个this是异步操作回调对象，该对象需要实现CompletionHandler接口 */
        client.connect(new InetSocketAddress(host, port), this, this);
        try{
			/* 此处闭锁用于控制线程的退出，结合后面的countDown()进行控制 */
            latch.await();
        }catch (InterruptedException el) {
            el.printStackTrace();
        }

        try{
            client.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

	/* 异步连接成功之后的方法回调completed()，CompletionHandler接口的方法 */
    @Override
    public void completed(Void result, AIOClientRunner attachment) {
		/* 创建请求消息体req */
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
		/* 调用AsynchronousSocketChannel的write方法进行异步写，第二个参数的匿名类用于写操作完成后的回调 */ 
        client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if(buffer.hasRemaining()) {
					/* 缓冲区有尚未发送的字节，继续异步发送 */
                    client.write(buffer, buffer, this);
                }else{
					/* 异步读取服务器应答消息的处理逻辑 */
                    ByteBuffer  readBuffer = ByteBuffer.allocate(1024);
					/* 调用AsynchronousSocketChannel的read方法异步读取服务端的响应消息 */
                    client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
							/* 在匿名类的回调里读取应答消息，打印结果，使用latch闭锁关闭放开本线程阻塞让线程执行完毕关闭客户端 */
                            attachment.flip();
                            byte[] bytes = new byte[attachment.remaining()];
                            attachment.get(bytes);
                            String body;
                            try{
                                body = new String(bytes, "UTF-8");
                                System.out.println("Now is : " + body);
                                latch.countDown();
                            }catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try{
                                client.close();
                                latch.countDown();
                            }catch (IOException e) {
                                // ignore on close
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try{
                    client.close();
                    latch.countDown();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AIOClientRunner attachment) {
        exc.printStackTrace();
        try{
            client.close();
            latch.countDown();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
