package com.zxf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
	// 提取数据库每列的公共配置项，作为元数据，消除重复，同时提供默认值消除减轻使用负担
	boolean primaryKey() default false;
	boolean allowNull() default true;
	boolean unique() default false;
}
