package com.junit.impl;

import com.junit.Request;
import com.junit.Response;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/06/30 15:51
 */
public class ErrorResponse implements Response {
	private Request originalRequest;

	private Exception originalException;

	public ErrorResponse( Request request, Exception exception )
	{
		this.originalRequest = request;
		this.originalException = exception;
	}

	public Request getOriginalRequest()
	{
		return this.originalRequest;
	}

	public Exception getOriginalException()
	{
		return this.originalException;
	}
}

