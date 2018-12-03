package com.zxf.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: VoControllerAdvice
 * @Description: 定义控制器通知来处理异常
 * @Author: ZhangXuefeng
 * @Date: 2018/12/3 23:40
 * @Version: 1.0
 **/
/*ControllerAdvice表示定义一个控制器通知*/
@ControllerAdvice(
        /*指定拦截包*/
        basePackages = {"com.zxf.springmvc.controller.*"},
        /*限定被标注为@Controller或者@RestController的类才能被拦截*/
        annotations = {Controller.class, RestController.class})
public class VoControllerAdvice {

    /*@ExceptionHandler注解指定捕获异常后的处理方法*/
    /*异常处理，可以定义异常类型进行拦截处理，此处指定拦截NotFoundException异常*/
    @ExceptionHandler(value = NotFoundException.class)
    /*拦截异常后以JSON的方式作为响应返回给前端*/
    @ResponseBody
    /*定义服务器错误状态码*/
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> exception(HttpServletRequest request, NotFoundException ex) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("code", ex.getCode());
        msgMap.put("message", ex.getCustomMessage());
        return msgMap;
    }
}
