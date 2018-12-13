package com.zxf.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 *  @ClassName: WebSecurityCustomedConfig
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/13 19:00
 *  @Version: 1.0
 **/
@Configuration
public class WebSecurityCustomedConfig extends WebSecurityConfigurerAdapter {
	@Value("${system.user.password.secret}")
	private String secret;

	@Autowired
	private UserDetailsService userDetailsService;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secret);
		/*自定义UserDetailsService接口的实现类，实现中自定义用户名，密码，权限三者关系*/
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}
}
