package com.zxf.spring.webflux.config;

import com.zxf.spring.webflux.dao.User;
import com.zxf.spring.webflux.enums.SexEnum;
import com.zxf.spring.webflux.validator.UserValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.concurrent.TimeUnit;

/**
 *  @ClassName: WebFluxConfig
 *  @Description: WebFlux的配置文件，细化配置时需要，实现接口所有方法都有默认实现
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/24 10:56
 *  @Version: 1.0
 **/
@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

	/**
	 * 设置全局性验证器，注意验证器只能设置一个，而且必然是全局性的，被所有Controller共享
	 * 要给每个Controller单独指定验证器，要在Controller内部使用@InitBinder注解指定
	 * */
	@Override
	public Validator getValidator() {
		return new UserValidator();
	}

	/**
	 * 加载转换器Converter和格式化器Formatter的方法
	 * 此处注册Converter，该注册器通过每个属性的字符串得到User对象
	*/
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(stringToUserConverter());
	}

	public Converter<String, User> stringToUserConverter() {
		Converter<String, User> converter = new Converter<String, User>() {
			@Override
			public User convert(String s) {
				String strArr[] = s.split("-");
				User user = new User();
				Long id = Long.valueOf(strArr[0]);
				user.setId(id);
				user.setUserName(strArr[1]);
				int sexCode = Integer.valueOf(strArr[2]);
				SexEnum sex = SexEnum.getSexEnum(sexCode);
				user.setSex(sex);
				user.setNote(strArr[3]);
				return user;
			}
		};
		return converter;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
					/*注册资源，可以通过URI访问*/
		registry.addResourceHandler("/resources/static/**")
					/*注册Spring资源，可以在Spring上下文机制中访问/public路径来访问classpath:/static/下的资源*/
				.addResourceLocations("/public", "classpath:/static/")
					/*缓存一年*/
				.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
	}
}
