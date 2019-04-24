package com.zxf.nio.server.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName: EchoServerHandler
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/24 23:45
 * @Version: 1.0
 **/
public class EchoServerHandler extends ChannelHandlerAdapter {
    int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /* DelimiterBasedFrameDecoder自动对请求消息进行了解码，保证后序对象就是个完整的消息包
        *  然后StringDecoder将ByteBuf解码成字符串对象，
        *  最后EchoServerHandler的本函数第二个参数msg就是解码后的字符串对象*/
        String body = (String)msg;
        System.out.println("This is " + ++counter + " times receive client : [" +
                body + "]");
        body += "$_";
        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
        /*  将原始消息返回给客户端作为应答，注意要补上被DelimiterBasedFrameDecoder过滤掉的分隔符$_ */
        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
