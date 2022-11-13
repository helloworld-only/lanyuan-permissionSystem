package com.authSys.config;

import com.authSys.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SysTokenAuthenticationFilter sysTokenAuthenticationFilter(){
        SysTokenAuthenticationFilter filter
                = new SysTokenAuthenticationFilter(userDetailsService);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AbstractAuthenticationProcessingFilter usernamePasswordAutnenticationFilter() throws Exception {
        AbstractAuthenticationProcessingFilter filter = new SysUsernamePasswordAuthenticationFilter();

        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new SysAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new SysAuthenticationFailureHandler());

        return filter;
    }


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

                // 禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()


                // 在获取用户名和密码前，先通过验证码过滤器验证一下验证码是否正确
                .addFilterBefore(new SysCaptchaResultFilter(), UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(sysTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                // 通过token获取到用户信息，而非通过session
//                .addFilterAt(usernamePasswordAutnenticationFilter(), UsernamePasswordAuthenticationFilter.class)


                // 登录处理不由SpringSecurity处理，由我们自己编写Controller实现
                .formLogin().disable()

                // 登录处理由SpringSecurity处理
//                .formLogin()
//                .usernameParameter("acct")
//                .passwordParameter("passwd")
//                .loginProcessingUrl("/home")
//                .successHandler(new SysAuthenticationSuccessHandler())  //认证成功的处理
//                .failureHandler(new SysAuthenticationFailureHandler())  // 认证失败的处理
//                .and()

                .logout()
                .logoutSuccessHandler(new SysLogoutSuccessHandler()) // 退出登录成功的处理
                .and()


                .authorizeRequests() //对请求进行授权

                .antMatchers(HttpMethod.OPTIONS).permitAll()

                .antMatchers("/captchaImg").permitAll()

                .antMatchers("/home").permitAll()



//                .regexMatchers("/home/.*")
                .antMatchers("/home/*")
                .access("hasAnyRole({'admin','selecter'}) or hasAuthority('Select')")


                .antMatchers("/home/*/add")
                .access("hasAnyRole({'admin'}) or hasAuthority('Insert')")


                .antMatchers("/home/*/delete/*/")
                .access("hasAnyRole({'admin'}) or hasAuthority('Delete')")


                .antMatchers("/home/*/update")
                .access("hasAnyRole({'admin'}) or hasAuthority('Update')")


                .antMatchers("/home/*/*/*Distribution")
                .access("hasRole('admin') or hasAuthority('Select')")


                .antMatchers("/home/*/*/*Distribution/add")
                .access("hasRole('admin') or hasAuthority('Insert')")

                .antMatchers("/home/*/*/*Distribution/delete/*/")
                .access("hasRole('admin') or hasAuthority('Delete')")

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
