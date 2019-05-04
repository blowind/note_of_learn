package com.zxf.nio.httpxml.server;

import com.zxf.nio.httpxml.common.HttpXmlRequestDecoder;
import com.zxf.nio.httpxml.common.HttpXmlResponseDecoder;
import com.zxf.nio.httpxml.common.HttpXmlResponseEncoder;
import com.zxf.nio.httpxml.pojo.Order;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * @ClassName: HttpXmlServer
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 12:14
 * @Version: 1.0
 **/
public class HttpXmlServer {
    public void run(final int port, String host) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("xml-decoder", new HttpXmlRequestDecoder(Order.class, true));

                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            ch.pipeline().addLast("xml-encoder", new HttpXmlResponseEncoder());
                            ch.pipeline().addLast("xmlServerHandler", new HttpXmlServerHandler());

                        }
                    });
            ChannelFuture f = b.bind(new InetSocketAddress(host, port)).sync();
            System.out.println("HTTP订购服务器启动，网址是: " + "http://localhost:" + port);
            f.channel().closeFuture().sync();
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
        new HttpXmlServer().run(port, "127.0.0.1");
    }
}
