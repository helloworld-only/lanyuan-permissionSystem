package com.authSys.config;

import com.authSys.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

//@Configuration
public class SpringMvcConfig {

    @Bean
    public HandlerInterceptor getInterceptor(){
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        return loginInterceptor;
    }
}
