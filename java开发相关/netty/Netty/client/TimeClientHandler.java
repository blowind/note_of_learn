package com.zxf.nio.client.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName: TimeClientHandler
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/23 22:45
 * @Version: 1.0
 **/
public class TimeClientHandler extends ChannelHandlerAdapter {
    private int counter;
    private byte[] req;



    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    /* 当客户端和服务端TCP链路建立成功后，NIO线程会调用channelActive方法 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message;
        for(int i=0; i<100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }

    }

    /* 服务端返回应答消息时，channelRead方法被调用 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        /*不考虑粘包/拆包问题，直接读取服务端返回数据的写法，此时buf里面是TCP运送过来的码流，有多少读多少，没有分段*/
        /*ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");*/

        String body = (String)msg;
        System.out.println("Now is : " + body + " ; the counter is : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
