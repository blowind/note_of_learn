package com.zxf.nio.httpxml.client;

import com.zxf.nio.httpxml.common.HttpXmlRequest;
import com.zxf.nio.httpxml.common.HttpXmlResponse;
import com.zxf.nio.httpxml.pojo.OrderFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName: HttpXmlClientHandler
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 12:07
 * @Version: 1.0
 **/
public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        /*构造HttpXmlRequest对象并发送*/
        HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse response) throws Exception {
        System.out.println("The client receive response of http header  is : " + response.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is : " + response.getResult());
    }
}
