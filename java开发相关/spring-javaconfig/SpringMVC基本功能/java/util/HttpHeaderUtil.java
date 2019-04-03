package com.zxf.springmvc.util;

import javax.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *  @ClassName: HttpHeaderUtil
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/4/3 10:01
 *  @Version: 1.0
 **/
public class HttpHeaderUtil {

	public static boolean isIpInvalid(String ip) {
		if(ip == null || ip.length() == 0 || ip.trim().isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			return true;
		}else{
			return false;
		}
	}

	public static String getIp(HttpServletRequest request) {
		if(request == null) {
			return "";
		}

		/* nginx反向代理约定的协议头，填写ng看到的REMOTE_ADDR */
		String ip = request.getHeader("X-Real-IP");
		if(isIpInvalid(ip)) {
			/* 用apache http做代理时一般会加上Proxy-Client-IP请求头 */
			ip = request.getHeader("Proxy-Client-IP");
		}

		if(isIpInvalid(ip)) {
			/* 约定的多代理情况下的HTTP HEDDER项，格式为 X-Forwarded-For: client, proxy1, proxy2
			 *  一般通过了HTTP代理或者负载均衡服务器时会添加该项*/
			ip = request.getHeader("X-Forwarded-For");
		}

		if(isIpInvalid(ip)) {
			/* weblogic插件给HTTP HEADER添加的头 */
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if(isIpInvalid(ip)) {
			/* 有些代理服务器会加上此请求头 */
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if(isIpInvalid(ip)) {
			/*获取REMOTE ADDR，这个IP是Web服务器TCP连接的IP，在外部客户端无代理直连服务器时表示的用户真实IP
			* 有多个代理时，获取的是最后一个代理所在机器的ip*/
			ip = request.getRemoteAddr();
		}

		/* X-Forwarded-For等多个ip地址混合的情况，第一个ip是客户端ip，其他是proxy的ip */
		int index = ip.indexOf(',');
		if(index > 0) {
			return ip.substring(0, index);
		}

		/* 如果是通过127.0.0.1本地地址访问的，则获取本机IP返回，注意localhost地址会被翻译成0:0:0:0:0:0:0:1，此处没做处理 */
		if("127.0.0.1".equals(ip)) {
			try{
				ip = InetAddress.getLocalHost().getHostAddress();
			}catch (UnknownHostException uhe) {
				uhe.printStackTrace();
			}
		}
		return ip;
	}

}
