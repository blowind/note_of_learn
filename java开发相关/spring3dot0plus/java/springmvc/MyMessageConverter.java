package com.zxf;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class MyMessageConverter extends AbstractHttpMessageConverter<DemoObj> {
	public MyMessageConverter() {
	/*	新建一个自定义的媒体类 application/zxf */
		super(new MediaType("application", "zxf", Charset.forName("UTF-8")));
	}

	/*  处理请求的数据，此处使用-分隔的数据初始化 DemoObj 对象 */
	@Override
	protected DemoObj readInternal(Class<? extends DemoObj> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		String temp = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
		String[] tempArr = temp.split("-");
		return new DemoObj(new Long(tempArr[0]), tempArr[1]);
	}

	/*  返回true表明需要支持的对象的类型 */
	@Override
	protected boolean supports(Class<?> clazz) {
		return DemoObj.class.isAssignableFrom(clazz);
	}

	/*  关于如何输出数据到response，此处我们在原样上加上hello */
	@Override
	protected void writeInternal(DemoObj obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		String out = "hello: " + obj.getId() + "-" +obj.getName();
		outputMessage.getBody().write(out.getBytes());
	}
}
