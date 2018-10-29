package com.zxf.zxfspring.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 16:03
 */
public class DataSourceCondition implements Condition {

	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment env = context.getEnvironment();
		return env.containsProperty("jdbc.database.driver")
				&& env.containsProperty("jdbc.database.url")
				&& env.containsProperty("jdbc.database.username")
				&& env.containsProperty("jdbc.database.password");
	}
}
