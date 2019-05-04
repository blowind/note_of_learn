package com.zxf.nio.httpxml.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderUtil.setContentLength;

/**
 * @ClassName: HttpXmlResponseEncoder
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 1:17
 * @Version: 1.0
 **/
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse xmlResponse, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, xmlResponse.getResult());
        FullHttpResponse response = xmlResponse.getHttpResponse();
        if(response == null) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
        }else{
            response = new DefaultFullHttpResponse(xmlResponse.getHttpResponse().protocolVersion(), xmlResponse.getHttpResponse().status(), body);
        }
        response.headers().set(CONTENT_TYPE, "text/xml");
        System.out.println("lenth : " + body.readableBytes());
        setContentLength(response, body.readableBytes());
        out.add(response);
    }
}
