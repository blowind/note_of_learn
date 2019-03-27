package com.zxf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
	int value() default 0; // VARCHAR最大长度
	String name() default "";
	Constraints constraints() default @Constraints;  // 嵌套注解，使用@Constraints的默认值作为默认值
}
