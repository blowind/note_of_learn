package com.zxf.aop;

import org.springframework.stereotype.Service;

@Service
public class DemoAnnotationService {
    @Action(name="注解式拦截的add操作")
    public void add() {}  //  使用注解的被拦截类
}
