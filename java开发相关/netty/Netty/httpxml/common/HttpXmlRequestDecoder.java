package com.zxf.nio.httpxml.common;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @ClassName: HttpXmlRequestDecoder
 * @Description: 具体业务类的解码器，调用AbstractHttpXmlDecoder的公共xml到pojo解码方法得到pojo对象，将生成结果包装到HttpXmlRequest中给HttpXmlServerHandler使用
 *
 *   注意：MessageToMessageDecoder类会在decode之后的finally中调用ReferenceCountUtil.release()方法，减掉FullHttpRequest的计数器，会导致HttpXmlServerHandler来处理时对象计数器小于等于0从而抛出IllegalReferenceCountException异常，所以所有继承MessageToMessageDecoder的子类都要调用ReferenceCountUtil.retain()方法维护计数器
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 0:56
 * @Version: 1.0
 **/
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {

    public HttpXmlRequestDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    /* 第二个参数表示是否打印HTTP休息提码流开关 */
    public HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> msg) throws Exception {
        if(!request.decoderResult().isSuccess()) {
            sendError(ctx, BAD_REQUEST);
            return;
        }

        HttpXmlRequest xmlRequest = new HttpXmlRequest(request, decode0(ctx, request.content()));

        /* 维护计数器技术，供自定义handler使用 */
        ReferenceCountUtil.retain(request);
        msg.add(xmlRequest);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
