package com.zxf.nio.httpfile;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName: HttpFileServer
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/29 22:30
 * @Version: 1.0
 **/
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src";

    public void run(final int port ,final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    /* 服务端都是先添加解码器，再加编码器，最后添加自定义handler */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /* 添加HTTP请求消息解码器  */
                            ch.pipeline().addLast("httpfile-decoder", new HttpRequestDecoder());
                            /* HttpObjectAggregator解码器的作用是将多个消息转换为单一的FullHttpRequest或者FullHttpResponse，处理HTTP解码器在每个HTTP消息中生成的多个消息对象 */
                            ch.pipeline().addLast("httpfile-aggregator", new HttpObjectAggregator(65536));
                            /* 添加HTTP响应编码器 */
                            ch.pipeline().addLast("httpfile-encoder", new HttpResponseEncoder());
                            /* 用于支持异步发送大的码流，防止大的文件传输这类操作占用过多内存导致Java内存溢出 */
                            ch.pipeline().addLast("httpfile-chunked", new ChunkedWriteHandler());
                            /* 具体业务处理的handler */
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });

            ChannelFuture future = b.bind("192.168.5.100", port).sync();
            System.out.println("HTTP 文件目录服务器启动，网址是：" + "http://192.168.5.100:" + port + url);
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {}
        }
        String url = DEFAULT_URL;
        if(args.length > 1) url = args[1];
        new HttpFileServer().run(port, url);
    }
}
