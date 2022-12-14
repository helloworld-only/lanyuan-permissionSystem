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
            ?   ?????????????????????
            *   ??????0?????????????????????????????????
            **  ??????0??????????????????
        */

        security
                .csrf()
                .disable()

                // ??????session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()


                // ?????????????????????????????????????????????????????????????????????????????????????????????
                .addFilterBefore(new SysCaptchaResultFilter(), UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(sysTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                // ??????token????????????????????????????????????session
//                .addFilterAt(usernamePasswordAutnenticationFilter(), UsernamePasswordAuthenticationFilter.class)


                // ??????????????????SpringSecurity??????????????????????????????Controller??????
                .formLogin().disable()

                // ???????????????SpringSecurity??????
//                .formLogin()
//                .usernameParameter("acct")
//                .passwordParameter("passwd")
//                .loginProcessingUrl("/home")
//                .successHandler(new SysAuthenticationSuccessHandler())  //?????????????????????
//                .failureHandler(new SysAuthenticationFailureHandler())  // ?????????????????????
//                .and()

                .logout()
                .logoutSuccessHandler(new SysLogoutSuccessHandler()) // ???????????????????????????
                .and()


                .authorizeRequests() //?????????????????????

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

                .antMatchers("/**").hasRole("admin")   //??????admin????????????????????????

                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()    //???????????????????????????



                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new SysAuthenticationEntryPoint()) // ?????????????????????????????????????????????

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new SysAccessDeniedHandler()) // ??????????????????????????????????????????
        ;




    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                ;
    }
}
