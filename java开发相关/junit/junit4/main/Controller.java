package com.junit;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/06/30 15:39
 */
public interface Controller {
	Response processRequest(Request request);

	void addHandler(Request request, RequestHandler requestHandler);
}
