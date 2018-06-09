package com.zxf.bootsecurity.config;

import com.zxf.bootsecurity.security.CustomPasswordEncoder;
import com.zxf.bootsecurity.security.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    /* 重载配置 user-detail服务 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /***  使用内存留存信息进行认证，注意roles字段都会自动补上 ROLES_ 前缀， ***/
//        auth.inMemoryAuthentication().passwordEncoder(new CustomPasswordEncoder()).withUser("zxf").password("zxf").roles("ADMIN").and().withUser("ssy").password("ssy").roles("USER");
        /****  spring 5.0之后的版本强制要求加上密码编码器  ****/
//        auth.userDetailsService(customUserService()).passwordEncoder(new CustomPasswordEncoder());
    }

    /* 重载通过拦截器保护请求 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** 注意此处设置了登录页面login和登录成功跳转页面index，因此在WebMvcConfig中要将域名和模板名进行映射 **/
        http.authorizeRequests().anyRequest().authenticated()
             .and().formLogin().loginPage("/login")
                // 此处指定login成功后走/index路径，则controller一定要有响应的mapping
             .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
                //开启cookie保存用户数据 设置cookie有效期(单位为秒)和私钥
             .and().rememberMe().tokenValiditySeconds(5).key("abc123")
                //对于注销功能，给与所有人权限
             .and().logout().permitAll();
    }
}
