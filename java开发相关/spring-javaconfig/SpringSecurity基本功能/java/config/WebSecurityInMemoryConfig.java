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
//@Configuration
public class WebSecurityInMemoryConfig extends WebSecurityConfigurerAdapter {


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
                    /* roles()方法底层实际使用的方法，此处需要补上ROLE_前缀的完整形式*/
                .authorities("ROLE_USER");
    }

    /** 主要用来配置Filter链的内容，可以配置Filter链忽略哪些内容
     *  即哪些页面需要验证，哪些不用验证直接放行
     * @param web  Spring Web Security对象
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    /** 用来配置拦截保护的请求，哪些请求放行，哪些请求需要验证
     * 即指定用户、角色与对应URL的访问权限
     *
     * 此处展示了配置请求路径访问权限的基本形式，其他两个配置Class同名函数还展示了其他配置样式
     * @param http  http安全请求对象
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*此处的配置，采取先配置优先的原则，
        例如此处配置其他路径可以签名访问，后面紧接着又配置了允许匿名访问，最终签名访问生效*/
        /*限定签名后的权限*/
        http.authorizeRequests()
                /*限定/user/welcome这两个请求赋予角色ROLE_USER或者ROLE_ADMIN*/
                .antMatchers("/user/welcome", "/user/details").hasAnyRole("USER", "ADMIN")
                /*采用ant风格的路径匹配方式 限定/admin/下所有请求权限赋予角色ROLE_ADMIN*/
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                  /*采用正则表达式风格的路径匹配方式，效果同上*/
                /*.regexMatchers("/admin/.*").hasAuthority("ROLE_ADMIN")*/
                /*其他路径允许签名后访问*/
                .anyRequest().permitAll()
                /*and代表连接词，对于没有配置权限的其他请求允许匿名访问*/
                .and().anonymous()
                /*使用Spring Security默认的登录页面*/
                .and().formLogin()
                /*启动HTTP基础验证*/
                .and().httpBasic();
    }
}
