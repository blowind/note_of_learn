package com.zxf.nio.httpxml.pojo;

import org.jibx.runtime.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @ClassName: TestOrder
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/1 21:38
 * @Version: 1.0
 **/
public class TestOrder {
    private IBindingFactory factory = null;
    private StringWriter writer = null;
    private StringReader reader = null;
    private final static String CHARSET_NAME = "UTF-8";

    private String encode2Xml(Order order) throws JiBXException, IOException {
        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        /* 通过工厂类创建指定对象（此处是Order）的数据编排上下文  */
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        /* 将传入对象按照指定编码格式（此处为UTF-8）序列化为StringWriter */
        mctx.marshalDocument(order, CHARSET_NAME, null, writer);
        /* 通过toString()方法得到String类型的XML对象 */
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr.toString());
        return xmlStr;
    }

    private Order decode2Order(String xmlBody) throws JiBXException {
        reader = new StringReader(xmlBody);
        /* 通过工厂类创建指定对象（此处是Order）的数据解编上下文  */
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        /* 将xml文件对应的StringReader解编为Order对象 */
        Order order = (Order)uctx.unmarshalDocument(reader);
        return order;
    }

    public static void main(String[] args) throws JiBXException, IOException {
        TestOrder test = new TestOrder();
        Order order = OrderFactory.create(123);
        String body = test.encode2Xml(order);
        Order order2 = test.decode2Order(body);
        System.out.println(order2);
    }
}
