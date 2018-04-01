package com.zxf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

@Configuration
@EnableWebMvc   /* 启动WebMVC的基本配置，配合 WebMvcConfigurerAdapter 生成网站所需的基本bean  */
@ComponentScan("com.zxf")
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    /*  返回基本的视图模板的配置  */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    /* 重写 addResourceHandlers 方法，实现注入js、css、图片等资源的直接访问  */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets?**").addResourceLocations("classpath:/assets/");
    }

    /* 生成拦截器的bean */
    @Bean
    public DemoInterceptor demoInterceptor() {
        return new DemoInterceptor();
    }

    /*  注册自定义拦截器，不注册的话默认没有任何拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoInterceptor());
    }

    /* 对于简单返回对应页面而无任何业务处理的求情，通过如下配置直接实现，
       省下了实现Controller类和RequestMapping的步骤 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/toUpload").setViewName("upload");
        registry.addViewController("/converter").setViewName("converter");
    }

    /* 配置 MultipartResolver 来上传文件 */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return multipartResolver;
    }

    /* 默认情况下，请求路径中 . 之后的内容被忽略，通过如下配置可以取消忽略 */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    /* 添加一个自定义的HttpMessageConverter，不覆盖默认注册的HttpMessageConverter
       另一实现方法是重载Override configureMessageConverters，这样会覆盖Spring MVC默认注册的多个HttpMessageConverter */

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
    }

    /*  生成MessageConverter转换器对应的bean */
    @Bean
    public MyMessageConverter converter() {
        return new MyMessageConverter();
    }
}
