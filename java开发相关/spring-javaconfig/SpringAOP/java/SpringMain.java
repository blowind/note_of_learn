package com.zxf.aop;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class SpringMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.zxf.aop");
        context.start();
    }
}
