package com.authSys.config;

import com.authSys.security.SysAccessDeniedHandler;
import com.authSys.security.SysAuthenticationEntryPoint;
import com.authSys.security.SysAuthenticationFailureHandler;
import com.authSys.security.SysAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        /*
            ?   匹配任何单字符
            *   匹配0或者任意字符数量的字符
            **  匹配0或者多级目录
        */

        security
                .csrf()
                .disable()

                .formLogin()
                .usernameParameter("acct")
                .passwordParameter("passwd")
                .loginProcessingUrl("/home")
                .successHandler(new SysAuthenticationSuccessHandler())  //认证成功的处理
                .failureHandler(new SysAuthenticationFailureHandler())  // 认证失败的处理

                .and()
                .authorizeRequests() //对请求进行授权

                .antMatchers(HttpMethod.OPTIONS).permitAll()

                .antMatchers("/captchaImg").permitAll()

                .antMatchers("/home").permitAll()

                .regexMatchers("/home/.*").access("hasRole(admin) or hasAuthority(Select)")
//                .antMatchers("/home/*")
//                .hasAuthority("Select")
//                .accessDecisionManager()

                .antMatchers("/home/*/add").hasAuthority("Insert")

                .antMatchers("/home/*/delete/*/").hasAuthority("Delete")

                .antMatchers("/home/*/update").hasAuthority("Update")

                .antMatchers("/home/*/*/*Distribution").hasAuthority("Select")

                .antMatchers("/home/*/*/*Distribution/add").hasAuthority("Insert")

                .antMatchers("/home/*/*/*Distribution/delete/*/").hasAuthority("Delete")

                .antMatchers("/**").hasRole("admin")   //对于admin，任何请求都通过

                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()    //需要登录后才能访问



                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new SysAuthenticationEntryPoint()) // 匿名用户访问无权限时的异常处理

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new SysAccessDeniedHandler()) // 用户访问权限不足时的异常处理
        ;




    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                ;
    }
}
