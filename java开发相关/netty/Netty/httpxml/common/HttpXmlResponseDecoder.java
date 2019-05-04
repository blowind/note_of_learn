package com.zxf.nio.httpxml.common;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * @ClassName: HttpXmlResponseDecoder
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 1:27
 * @Version: 1.0
 **/
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {
    public HttpXmlResponseDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    public HttpXmlResponseDecoder(Class<?> clazz, boolean isPrintlog) {
        super(clazz, isPrintlog);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          DefaultFullHttpResponse response, List<Object> out) throws Exception {
        HttpXmlResponse xmlResponse = new HttpXmlResponse(response, decode0(
                ctx, response.content()));
        out.add(xmlResponse);
    }
}
