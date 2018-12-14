package com.zxf.spring.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @ClassName: WebSecurityDatabaseConfig
 * @Description:  展示使用基本的使用数据库内容进行权限验证的方式，同时展示
 * @Author: ZhangXuefeng
 * @Date: 2018/12/4 23:52
 * @Version: 1.0
 **/
//@Configuration
public class WebSecurityDatabaseConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private DataSource dataSource;

    @Value("${system.user.password.secret}")
    private String secret;

    /*数据库中的available字段用做表示用户是否有效，1为有效，0为无效*/
    String pwdQuery = "select user_name, pwd, available from t_user where user_name = ?";
    String roleQuery = "select u.user_name, r.role_name "
            + " from t_user u, t_user_role ur, t_role r"
            + " where u.id = ur.user_id and r.id = ur.role_id"
            + " and u.user_name = ?";

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(this.secret);
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                    /*绑定数据源*/
                .dataSource(dataSource)
                    /*通过SQL查询的用户名、密码、有效位available来判断当前用户是否有效*/
                .usersByUsernameQuery(pwdQuery)
                    /*通过SQL查询的用户权限组来判断用户权限*/
                .authoritiesByUsernameQuery(roleQuery);
    }

    /**
     * 使用Spring表达式配置访问权限，此处主要是配置在access()函数中
     * @param http http安全请求对象
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*限定签名后的权限*/
        http.authorizeRequests()
                /*使用Spring表达式限定只有角色ROLE_USER或者ROLE_ADMIN可以访问*/
                .antMatchers("/user/**").access("hasRole('USER') or hasRole('ADMIN')")
                /*设置访问权限给角色ROLE_ADMIN，要求是完整登录(非记住我)*/
                .antMatchers("/admin/welcome1").access("hasAuthority('ROLE_ADMIN') && isFullyAuthenticated()")
                /*限定/admin/welcome2访问权限给角色ROLE_ADMIN，允许不完整登录*/
                .antMatchers("/admin/welcome2").access("hasAnyAuthority('ROLE_ADMIN')")
                /*使用记住我的功能*/
                .and().rememberMe()
                /*使用Spring Security默认的登录页面*/
                .and().formLogin()
                /*启动HTTP基础验证*/
                .and().httpBasic();
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder("abcdefg");
        System.out.println(passwordEncoder.encode("123456"));
    }
}
