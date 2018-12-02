package com.zxf.springmvc.interceptor;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: MyInterceptor
 * @Description: 拦截器的实现，用于拦截处理所有HTTP请求，JAVA8之后都提供了默认实现，根据需要实现相关函数即可
 * @Author: ZhangXuefeng
 * @Date: 2018/12/2 10:01
 * @Version: 1.0
 **/
/*不是通过注册为bean，而是通过在config里面注册拦截器进行配置*/
public class MyInterceptor implements HandlerInterceptor {

    /*在控制器处理之前拦截请求并操作，返回true继续执行，返回false直接返回*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("处理器前方法");
        return true;
    }

    /*在控制器处理之后，视图渲染之前拦截请求和应答并操作*/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("处理器后方法");

        /*为空证明@ResponseBody注解的返回不走modelAndView渲染流程*/
        if(modelAndView == null) {
            System.out.println("modelAndView is null");
        }else{
            modelAndView.addObject("postHandler", "added in postHandler");
        }
    }

    /*在视图渲染完毕，返回给用户之前进行处理*/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("处理器完成方法");
//        System.out.println(handler.getClass().getCanonicalName());
    }
}
