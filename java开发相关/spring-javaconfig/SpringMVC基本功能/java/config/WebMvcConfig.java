package com.zxf.springmvc.config;

import com.zxf.springmvc.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.*;

import java.util.Locale;

/**
 * @ClassName: WebMvcConfig
 * @Description: 注册拦截器
 * @Author: ZhangXuefeng
 * @Date: 2018/12/2 10:09
 * @Version: 1.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private LocaleChangeInterceptor lci;

    /*
    国际化解析器：
    AcceptHeaderLocaleResolver: 使用浏览器头请求信息去实现国际化区域，Spring默认选择
    FixedLocaleResolver: 固定的国际化区域
    CookieLocaleResolver: 将国际化区域信息设置在浏览器Cookie中
    SessionLocaleResolver: 类似于CookieLocaleResolver，将国际化信息设置在session中，最常用的让用户选择国际化的手段
    */

    /*国际化解析器，注意这个Bean Name腰围localeResolver，否则Spring不会感知这个解析器*/
    @Bean(name="localeResolver")
    public LocaleResolver initLocaleResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 设置默认国际化区域
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }

    /*创建国际化拦截器*/
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        if(lci != null) {
            return lci;
        }
        lci = new LocaleChangeInterceptor();
        /*设置一个名为language的参数，拦截器会读取HTTP请求中的该参数，用以设置国际化参数，这样可以通过这个参数的变化来设置用户的国际化区域
        * 前端页面发送过一次请求后，该参数的值就保存在Session中了，除非显示变更，否则一直可用*/
        lci.setParamName("language");
        return lci;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*拦截器拦截顺序与此处add的先后顺序相关，按照先注册的先pre后post原则*/
        /*注册拦截器到SpringMVC机制，返回一个拦截器注册对象*/
        InterceptorRegistration ir = registry.addInterceptor(new MyInterceptor());
        /*使用正则式指定拦截匹配模式，限制拦截器拦截的请求*/
        ir.addPathPatterns("/interceptor/*");

        /*给处理器添加国际化拦截器*/
        registry.addInterceptor(localeChangeInterceptor());
    }
}
