package com.zxf.run;


import com.zxf.prepost.BeanWayService;
import com.zxf.prepost.JSR250WayService;
import com.zxf.prepost.PrePostConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrePostConfig.class);

        BeanWayService beanWayService = context.getBean(BeanWayService.class);
        JSR250WayService jsr250WayService = context.getBean(JSR250WayService.class);

        context.close();
    }
}
