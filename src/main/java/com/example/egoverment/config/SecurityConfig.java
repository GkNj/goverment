package com.example.egoverment.config;

import com.example.egoverment.filter.KaptchaAuthenticationFilter;
import com.example.egoverment.handler.CustomAccessDeniedHandler;
import com.example.egoverment.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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


        //ROOT 管理员 HR 人力资源顾问 MOF 财政部经理  MUD 城建部经理 MOR 交通部经理
        //MON新闻部经理 MOL后勤部经理 MOI新闻部职员 MFS财政部职员  MUC城建部职员 MCS 交通部职员 LS后勤部职员
        //定制请求的授权规则
        http.authorizeRequests()
                .antMatchers("/", "/index").hasAnyRole("ROOT", "HR", "MOF", "MUD", "MUR", "MUN", "MOL", "MOI", "MFS", "MUC", "MCS", "LS")
                .antMatchers("/user/findLoginUser", "/user/findUser", "/user/editorUser").permitAll()
                .antMatchers("/user/clockOut", "/user/clockIn", "/user/saveSalary", "/user/findUserBySalary",
                        "/user/findByPosition", "/user/findAllRole", "/user/checkUsername", "/user/deleteUser", "/user/updateUser", "/user/addUser").hasAnyRole("ROOT", "HR")
                .antMatchers("/document/official_document_design.html").hasAnyRole("ROOT", "HR", "MOF", "MUD", "MUR", "MUN", "MOL", "MOI", "MFS", "MUC", "MCS", "LS")
                .antMatchers("/document/official_document_upload.html").hasAnyRole("ROOT", "HR", "MOF", "MUD", "MUR", "MUN", "MOL", "MOI", "MFS", "MUC", "MCS", "LS")
        ;


        http.addFilterBefore(new KaptchaAuthenticationFilter("/login", "/login?error"), UsernamePasswordAuthenticationFilter.class);
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
