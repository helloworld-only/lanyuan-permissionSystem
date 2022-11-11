package com.authSys.security;

import com.authSys.domain.Constants;
import com.authSys.domain.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// AuthenticationEntryPoint主要是用来处理匿名用户访问无权限资源时的异常（即未登录，或者登录状态过期失效）
//@Component
public class SysAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        String token = httpServletRequest.getParameter(Constants.TOKEN);

        ResponseResult ajax = new ResponseResult();

        if(e instanceof BadCredentialsException){
            ajax.setCode(400);
            ajax.setMsg("登录信息有误，请重新登录");
        }else{
           ajax.setCode(401);
           ajax.setMsg("未登录");
        }
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(ajax);

        httpServletResponse.getWriter().print(s);
    }
}
