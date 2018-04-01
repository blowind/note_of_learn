package com.zxf;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*  设计一个基本拦截器，用来计算一次http访问的处理时间，具体bean的生成在 config文件中配置  */
public class DemoInterceptor extends HandlerInterceptorAdapter {

	/* 填入在请求发生前需要执行的动作 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws  Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		return true;
	}

	/* 填入在请求发生后需要执行的动作 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		long startTime = (Long)request.getAttribute("startTime");
		request.removeAttribute("startTime");
		long endTime = System.currentTimeMillis();
		System.out.println(("本次请求处理时间为: " + new Long(endTime-startTime)));
		request.setAttribute("handlingTime", endTime - startTime);
	}
}
