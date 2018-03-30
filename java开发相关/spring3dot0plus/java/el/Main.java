package com.zxf.config;


import com.zxf.el.ElConfig;
import com.zxf.scope.DemoPrototypeService;
import com.zxf.scope.DemoSingletonService;
import com.zxf.scope.ScopeConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);

        ElConfig  resourceService = context.getBean(ElConfig.class);
        resourceService.outputResource();

        context.close();
    }
}
