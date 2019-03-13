package com.example.egoverment.config;

import com.example.egoverment.handler.CustomAccessDeniedHandler;
import com.example.egoverment.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //允许跨域请求
        http.cors().disable();
        http.csrf().disable();

        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/", "/index").hasAnyRole("ROOT","MUC")
        ;


        //开启自动配置的登录功能
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureForwardUrl("/login")
                .defaultSuccessUrl("/index")
        ;
        //1./login来到登录页
        //2、重定向到、login?error表示登录失败
        //1./login来到登录页

        //登出配置
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        //自动登录配置
        http.rememberMe();

        //403处理
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

    }

    /**
     * 数据加密设置
     * 采用BCrypt加密
     *
     * @return 加密后密码
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }

    /**
     * 不拦截资源
     *
     * @param web {@link WebSecurity}
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/media/**", "/uedtior/**");
    }

    /**
     * 账户密码配置
     *
     * @param auth 权限
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}
