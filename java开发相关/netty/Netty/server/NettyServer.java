package com.zxf.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *  @ClassName: NettyServer
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/4/23 13:52
 *  @Version: 1.0
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
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
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
