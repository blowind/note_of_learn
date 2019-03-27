package com.zxf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Uniqueness {
	// 嵌套注解时，根据具体需求修改默认值的写法
	Constraints constraints() default @Constraints(unique = true);
}
