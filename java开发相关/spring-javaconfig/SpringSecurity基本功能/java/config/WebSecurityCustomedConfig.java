package com.zxf.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 *  @ClassName: WebSecurityCustomedConfig
 *  @Description: 展示使用自定义的验证实现提供权限验证服务的方式（内部实现可以基于数据库或者redis）
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

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secret);
		/*自定义UserDetailsService接口的实现类，实现中自定义用户名，密码，权限三者关系*/
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	/**
	 *  强制使用HTTPS验证
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*requiresChannel()说明使用安全渠道，requiresSecure()限定使用https请求*/
		http.requiresChannel().antMatchers("/admin/**").requiresSecure()
				/*requiresInsecure()不限定使用https请求*/
				.and().requiresChannel().antMatchers("/user/**").requiresInsecure()
				/*限定允许的访问角色*/
				.and().authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN")
				.antMatchers("/user/**").hasAnyRole("ROLE", "ADMIN");

		/*关闭跨站请求伪造过滤器，不推荐，会有跨站请求攻击的危险*/
		/*http.csrf().disable();*/
	}
}
