package com.zxf.zxfbatis.simple.factory;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.List;
import java.util.Properties;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 *
 * 对象工厂，用于数据库行内容转换成对应的对象，
 * 基本不需要定制，都是使用默认的DefaultObjectFactory
 * @date 2018/10/19 16:57
 */
@Slf4j
public class MyObjectFactory extends DefaultObjectFactory {

	private static final long serialVersionUID = -88551223568943131L;

	private Object temp = null;

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		log.info("初始化参数：{}", properties.toString());
	}

	@Override
	public <T> T create(Class<T> type) {
		T result = super.create(type);
		log.info("创建对象: {}", result.toString());
		log.info("是否和上次创建的是同一个对象: {}", temp == result);
		return result;
	}

	@Override
	public <T> T create(Class<T> type, List<Class<?>> constructorArgType, List<Object> constructorArgs) {
		T result = super.create(type, constructorArgType, constructorArgs);
		temp = result;
		return result;
	}

	@Override
	public <T> boolean isCollection(Class<T> type) {
		return super.isCollection(type);
	}

}
