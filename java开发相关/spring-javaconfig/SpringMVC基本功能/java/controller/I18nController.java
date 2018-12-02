package com.zxf.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @ClassName: I18nController
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/12/2 19:53
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/i18n")
public class I18nController {

    /*
    使用SpringMVC提供的国际化消息源机制：MessageSource接口体系，用于装载国际化消息
    其中其子类ResourceBundleMessageSource即是此处使用的方式
    */

    @Autowired
    private MessageSource messageSource;

    /*
    http://localhost:8080/i18n/page  默认请求
    http://localhost:8080/i18n/page?language=zh_CN   指定中文参数的请求
    http://localhost:8080/i18n/page?language=en_US   指定英文参数的请求
    */
    @GetMapping("/page")
    public String page(HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String msg = messageSource.getMessage("msg", null, locale);
        System.out.println("msg=" + msg);
        System.out.println(messageSource.getClass().getCanonicalName());
        return "i18n";
    }
}
