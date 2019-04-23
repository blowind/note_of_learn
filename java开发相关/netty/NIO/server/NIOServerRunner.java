package com.zxf.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NIOServerRunner
 * @Description: 实际执行Socket内容处理的具体类，
 *      负责轮询多路复用器Selector，可以处理多个客户端的并发接入
 * @Author: ZhangXuefeng
 * @Date: 2019/1/3 21:39
 * @Version: 1.0
 **/
public class NIOServerRunner implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverChannel;

    private volatile boolean stop;

    public NIOServerRunner(int port) {
        try{
            /*创建多路复用器*/
            selector = Selector.open();
            /*打开Socket通道*/
            serverChannel = ServerSocketChannel.open();
            /*设置为异步非阻塞模式*/
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);
            /*将Socket通道注册到selector并监听SelectionKey.OP_ACCEPT操作位*/
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port: " + port);
        }catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while(!stop) {
            try{
                /*遍历循环selector，休眠时间为1秒*/
                selector.select(1000);
                /*返回就绪状态的Channel集合的selectedKey集合*/
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while(it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e) {
                        if(key != null) {
                            key.cancel();
                            if(key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            }catch (Throwable t) {
                t.printStackTrace();
            }
        }

        /*多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，
        所以不需要重复释放资源*/
        if(selector != null) {
            try{
                selector.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()) {
            /*处理新接入的客户端请求消息，通过SelectionKey的操作位判断获知网络事件的类型*/
            if(key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                /*接收客户端连接请求并创建SocketChannel实例,
                * 到此处相当于完成TCP三次握手，TCP物理链路正式建立*/
                SocketChannel sc = ssc.accept();
                /*设置为异步非阻塞模式*/
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }

            /*读取客户端的请求消息*/
            if(key.isReadable()) {
                SocketChannel sc = (SocketChannel)key.channel();
                /*由于客户端发送码流大小未知，暂定开闭1MB的缓冲器*/
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                /*非阻塞的读，一次性全部读取所有数据到readBuffer，返回读取的字节数*/
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0) {
                    /*flip操作将缓冲区当前的limit设置为position，position设置为0
                    用于后续对缓冲区的读取操作*/
                    readBuffer.flip();
                    /*创建字节数组bytes并将readBuffer中可读的字节复制到bytes中*/
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    /*将消息字节转换成可识别的字符串*/
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                            new Date().toString() : "Bad order";
                    /*将应答消息异步发送给客户端*/
                    doWrite(sc, currentTime);
                }else if( readBytes < 0) {
                    // 表示对端链路关闭
                    key.cancel();
                    /*关闭SocketChannel释放资源*/
                    sc.close();
                }else{
                    // 读到0字节，忽略
                }
            }
        }
    }

    /**
     * 消息异步返回函数
     */
    private void doWrite(SocketChannel channel, String response) throws IOException {
        if(response != null && response.trim().length() > 0) {
            /*将字符串编码成字节数组*/
            byte[] bytes = response.getBytes();
            /*创建ByteBuffer并调用其put操作将字节数组bytes内容复制到缓冲区*/
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            /*将缓冲区中的字节数组发送出去*/
            channel.write(writeBuffer);

            /*由于异步非阻塞模式下不保证一次把所有字节数组发送完毕，可能会出现写半包问题
            需要注册写操作，不断轮询Selector将没有发送完毕的ByteBuffer发送完毕
            然后通过ByteBuffer的hasRemain()判断消息是否发送完毕，此处未演示*/
        }
    }



}
