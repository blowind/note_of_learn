package com.zxf.nio.httpxml.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;

import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * @ClassName: AbstractHttpXmlEncoder
 * @Description: 抽取公共的POJO对象转XML的代码成抽象类，做公共的编排服务
 *            在client端用于将准备放入request中传输的POJO对象编排成传输包中的xml格式
 *            在server端用于将准备放入response中传输的POJO对象编排成传输包中的xml格式
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 0:33
 * @Version: 1.0
 **/
public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {
    IBindingFactory factory = null;
    StringWriter writer = null;
    final static String CHARSET_NAME = "UTF-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws Exception {
        /* 根据业务类body构造IBindingFactory对象，本项目中仅Order类被JiBX做了POJO和xml的映射 */
        factory = BindingDirectory.getFactory(body.getClass());
        writer = new StringWriter();
        /* 通过IBindingFactory构造Marshalling（数据编排）上下文，准备将Java对象编排成XML文件 */
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        /* 将对象按照指定格式（此处为UTF-8）编排为xml文件并放入StringWriter */
        mctx.marshalDocument(body, CHARSET_NAME, null, writer);
        /* 获取编排后的xml字符串 */
        String xmlStr = writer.toString();
        writer.close();
        writer = null;
        ByteBuf encodeBuf = Unpooled.copiedBuffer(xmlStr, UTF_8);
        return encodeBuf;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(writer != null) {
            writer.close();
            writer = null;
        }
    }
}
