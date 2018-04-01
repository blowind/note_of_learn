package com.zxf.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration   /*  组合Configuration元注解 */
@ComponentScan   /*  组合ComponentScan元注解 */
public @interface WiselyConfiguration {
    String[] value() default {};
}
