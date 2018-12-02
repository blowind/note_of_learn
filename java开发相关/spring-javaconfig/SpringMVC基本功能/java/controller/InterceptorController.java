package com.zxf.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: InterceptorController
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/12/2 10:14
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/interceptor")
public class InterceptorController {

    /*
    验证拦截器功能，返回json的情景
    http://localhost:8080/interceptor/json

    正常情况下在执行完控制器返回后，
    会启用结果解析器ResultResolver去解析结果
    此时会轮询注册HttpMessageConverter接口的实现类
    对所有@ResponseBody注解的方法会标记其结果类型为JSON
    由于SpringMVC默认加载MappingJackson2HttpMessageConverter这个实现类
    因此符合匹配，从而将结果转换为JSON，此时ModelAndView就返回null，
    相关的视图解析器和视图渲染不再执行
    */
    @GetMapping("/json")
    @ResponseBody
    public Map<String, Object> json() {
        System.out.println("执行处理器逻辑(json形式)");
        Map<String, Object> map = new HashMap<>();
        map.put("ok", "ok");
        return map;
    }

    /*
    验证拦截器功能，返回页面的情景
    http://localhost:8080/interceptor/view
    */
    @GetMapping("/view")
    public String view() {
        System.out.println("执行处理器逻辑(逻辑视图形式)");
        return "interceptor";
    }
}
