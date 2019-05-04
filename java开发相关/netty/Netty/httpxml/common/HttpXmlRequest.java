package com.zxf.nio.httpxml.common;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @ClassName: HttpXmlRequest
 * @Description: 用于实现和协议栈之间的解耦，具体的业务类通过body存储
 * @Author: ZhangXuefeng
 * @Date: 2019/5/2 0:51
 * @Version: 1.0
 **/
public class HttpXmlRequest {
    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    /**
     * @return the request
     */
    public final FullHttpRequest getRequest() {
        return request;
    }

    /**
     * @param request
     *            the request to set
     */
    public final void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    /**
     * @return the object
     */
    public final Object getBody() {
        return body;
    }

    /**
     * @param body
     *            the object to set
     */
    public final void setBody(Object body) {
        this.body = body;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HttpXmlRequest [request=" + request + ", body =" + body + "]";
    }
}
