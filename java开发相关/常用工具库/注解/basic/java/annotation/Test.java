package com.zxf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本注解只能用在静态无参函数上，这是由解析本注解的处理代码m.invoke(null)决定的，
 * 编译时无法识别改要求，只能在运行时识别
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}
