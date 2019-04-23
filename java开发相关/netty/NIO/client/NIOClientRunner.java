package com.zxf.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NIOClientRunner
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/1/3 22:30
 * @Version: 1.0
 **/
public class NIOClientRunner implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile  boolean stop;

    public NIOClientRunner(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try{
            selector = Selector.open();
            /*打开Socket通道*/
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try{
            doConnect();
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while(!stop) {
            try{
                selector.select(1000);
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
            }catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
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
            /*判断是否连接成功*/
            SocketChannel sc = (SocketChannel)key.channel();
            if(key.isConnectable()) {
                /* 说明服务端已返回syn-ack应答，此时调用finishConnect()对连接结果进行判断 */
                if(sc.finishConnect()) {
                    /* 返回true表明客户端连接成功，将SocketChannel注册到多路复用器selector上 */
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                }else{
                    System.exit(1);
                }
            }

            if(key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("Now is : " + body);
                    this.stop = true;
                }else if(readBytes < 0) {
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                }else{
                    // 读到0字节，忽略
                }
            }
        }
    }

    private void doConnect() throws IOException {
        if(socketChannel.connect(new InetSocketAddress(host, port))) {
            /* 连接成功，将socketChannel注册到多路复用器selector上 */
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        }else{
            /* 没有直接连成功，说明服务端没有返回TCP握手应答消息，但并不代表连接失败，
             * 将socketChannel注册到多路复用器selector上，注册OP_CONNECT
              * 当服务端返回TCP syn-ack消息后，Selector就能轮询到这个socketChannel处于连接就绪状态 */
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if(!writeBuffer.hasRemaining()) {
            System.out.println("Send order 2 server succeed.");
        }
    }
}
