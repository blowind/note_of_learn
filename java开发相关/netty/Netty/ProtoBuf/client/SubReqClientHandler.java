package com.zxf.nio.client.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SubReqClientHandler
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/28 18:20
 * @Version: 1.0
 **/
public class SubReqClientHandler extends ChannelHandlerAdapter {
    public SubReqClientHandler() {}

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for(int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUsername("milaoshu");
        builder.setProductName("Netty book for protobuf");
        List<String> address = new ArrayList<>();
        address.add("Nanjing");
        address.add("Hangzhou");
        address.add("Shanghai");
        builder.addAllAddress(address);
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
