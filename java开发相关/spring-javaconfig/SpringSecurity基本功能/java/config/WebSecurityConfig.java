package com.zxf.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName: WebSecurityConfig
 * @Description: 通过继承WebSecurityConfigurerAdapter抽象类得到Spring Security的默认功能
 *                 通过覆盖其默认方法，来自定义定制的安全拦截方案
 *
 *                 Spring 5的Security中都要求使用密码编码器，否则会抛出异常
 * @Author: ZhangXuefeng
 * @Date: 2018/12/4 11:20
 * @Version: 1.0
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**  用来配置用户签名服务，主要是user-details机制，可以给用户赋予角色
     *   具体来说就是定义用户、密码和角色的地方
     * @param auth 签名管理构造器，用来构造用户具体权限控制
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*密码编码器*/
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        /*使用内存签名服务，即用户信息存放在内存中，适合测试时快速搭建环境*/
        auth.inMemoryAuthentication()
                    /*设置密码编码器*/
                .passwordEncoder(passwordEncoder)
                    /*注册用户admin，密码为abc，并赋予USER和ADMIN的角色权限*/
                .withUser("admin")
                .password(passwordEncoder.encode("abc"))
                    /*此处给的角色名称实际在Spring Security内部处理时会加上前缀ROLE_*/
                .roles("USER", "ADMIN")
                    /*连接方法and，用于一次设置多个用户*/
                .and()
                    /*注册用户myuser，密码为123456，并赋予USER的角色权限*/
                .withUser("myuser")
                .password(passwordEncoder.encode("123456"))
                .roles("USER");

        /*第二种写法，与前面写法效果一样，注意roles()函数其实是authorities()函数的简写形式*/
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userConfig = auth.inMemoryAuthentication();
        /*withUser()方法本质上生成用户详情构造器UserDetailsBuilder*/
        userConfig.withUser("myuser2")
                .password(passwordEncoder.encode("654321"))
                .authorities("ROLE_USER");
    }

    /** 主要用来配置Filter链的内容，可以配置Filter链忽略哪些内容
     * @param web  Spring Web Security对象
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    /** 用来配置拦截保护的请求，哪些请求放行，哪些请求需要验证
     * 即指定用户、角色与对应URL的访问权限
     * @param http  http安全请求对象
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }
}
