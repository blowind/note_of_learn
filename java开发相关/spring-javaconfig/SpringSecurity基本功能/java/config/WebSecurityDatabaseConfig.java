package com.zxf.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;

/**
 * @ClassName: WebSecurityDatabaseConfig
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/12/4 23:52
 * @Version: 1.0
 **/
@Configuration
public class WebSecurityDatabaseConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Value("${system.user.password.secret}")
    private String secret;

    String pwdQuery = "select user_name, pwd, available from t_user where user_name = ?";
    String roleQuery = "select u.user_name, r.role_name "
            + " from t_user u, t_user_role ur, t_role r"
            + " where u.id = ur.user_id and r.id = ur.role_id"
            + " and u.user_name = ?";

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(this.secret);
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource)
                .usersByUsernameQuery(pwdQuery)
                .authoritiesByUsernameQuery(roleQuery);
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder("abcdefg");
        System.out.println(passwordEncoder.encode("123456"));
    }
}
