package com.zxf.nio.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaderUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaderUtil.setContentLength;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @ClassName: WebSocketServerHandler
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 20:18
 * @Version: 1.0
 **/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        /* 第一次握手请求是HTTP协议承载 */
        if(msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest)msg);
        }else if(msg instanceof WebSocketFrame) {
            /*建立连接后，接收到的是解码后的WebSocketFrame消息*/
            handleWebSocketFrame(ctx, (WebSocketFrame)msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        /*请求解析成功并且Upgrade头显示要升级成websocket，才往下走*/
        if(!request.decoderResult().isSuccess()
           || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        /* 构造握手工厂，构造握手响应消息返回给客户端 */
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null ,false);
        handshaker = wsFactory.newHandshaker(request);
        if(handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else{
            /* 此处调用添加WebSocketFrameDecoder和WebSocketFrameEncoder到ChannelPipeline中，后续服务端可以自动对WebSocket消息进行编解码 */
            handshaker.handshake(ctx.channel(), request);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if(frame instanceof CloseWebSocketFrame) {
            /*如果是关闭链路的控制消息，则调用close方法关闭WebSocket连接*/
            handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame.retain());
            return;
        }

        if(frame instanceof PingWebSocketFrame) {
            /*对于维持链路的ping消息，构造pong消息返回*/
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        /*本示例仅展示文本消息，不处理二进制消息*/
        if(!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }

        String req = ((TextWebSocketFrame)frame).text();
        System.out.println(ctx.channel() + " received " + req);
        ctx.channel().write(new TextWebSocketFrame(req + ", 欢迎使用Netty WebSocket服务，现在时刻: " + new Date().toString()));
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if(res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if(!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
