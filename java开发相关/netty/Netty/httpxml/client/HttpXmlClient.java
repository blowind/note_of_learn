package com.zxf.nio.httpxml.client;

import com.zxf.nio.httpxml.common.HttpXmlRequestEncoder;
import com.zxf.nio.httpxml.common.HttpXmlResponseDecoder;
import com.zxf.nio.httpxml.pojo.Order;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.InetSocketAddress;

/**
 * @ClassName: HttpXmlClient
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 11:44
 * @Version: 1.0
 **/
public class HttpXmlClient {

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            /*先解码http响应，首先是按照HTTP标准将二进制码流解码成HTTP应答消息*/
                            ch.pipeline().addLast("http-decoder", new HttpResponseDecoder());
                            /*解码过程会生成多个HTTP对象，使用本聚合器聚合成一个完成的HTTP应答对象*/
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            /*使用定制的http-xml解码器进一步将聚合出来的HTTP应答对象内容的ByteBuf码流解编成本地的POJO对象*/
                            ch.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true));

                            /*编码的时候是按照从尾到头的顺序调度执行的*/
                            /*使用netty的HTTP请求编码器工具先打包成HTTP请求对象*/
                            ch.pipeline().addLast("http-encoder", new HttpRequestEncoder());
//                            使用定制的http-xml编码器进一步打包成ByteBuf码流
                            ch.pipeline().addLast("xml-encoder", new HttpXmlRequestEncoder());
                            ch.pipeline().addLast("xmlClientHandler", new HttpXmlClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(new InetSocketAddress(host, port)).sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {}
        }
        new HttpXmlClient().connect(port, "127.0.0.1");
    }
}
