package com.zxf.nio.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @ClassName: NettyServer
 * @Description: 通过
 * @Author: ZhangXuefeng
 * @Date: 2019/4/23 22:18
 * @Version: 1.0
 **/
public class NettyServer {

    public void bind(int port) throws Exception {
		/* 配置服务端的NIO线程组，包含一组NIO线程，专门用于网络事件的处理 */
		/* bossGroup用于服务端接受客户端的连接 */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
		/* workerGroup用于进行SocketChannel的网络读写 */
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
			/* Netty用于启动NIO服务端的辅助启动类 */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
					/* 配置TCP参数 */
                    .option(ChannelOption.SO_BACKLOG, 1024)
					/* 绑定IO事件的处理类 */
                    .childHandler(new ChildChannelHandler());
			/* 绑定端口，调用同步阻塞方法sync等待绑定操作完成，返回一个异步操作的通知回调 */
            ChannelFuture f = b.bind(port).sync();

			/* 等待服务端监听端口关闭 */
            f.channel().closeFuture().sync();
        }finally {
			/* 释放线程池资源 */
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		/* 通过LineBasedFrameDecoder和StringDecoder组合成按行切换的文本解码器，用于支持TCP粘包/拆包 */
        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
			/* LineBasedFrameDecoder的工作原理是依次遍历ByteBuf中的可读字节，判断是否有\n或者\r\n，
               如果有，则以此位置为结束为止，其间的字节组成一行读出，
			   简单来说就是换行符为结束标志的解码器，支持携带结束符/不携带结束符两种解码方式，
			   同时支持配置单行最大长度，如果读取到最大长度依然没有换行符，则抛出异常并忽略已经读到的码流*/
            arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
			/* 将接收到的对象转换成字符串，然后继续调用后面的handler，此处即TimeServerHandler */
            arg0.pipeline().addLast(new StringDecoder());
            arg0.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if(args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {}
        }
        new NettyServer().bind(port);
    }
}
