package com.authSys.security;

import com.authSys.domain.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 登录失败的处理器
//@Component
public class SysAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseResult ajax = new ResponseResult();
        ajax.setCode(401);

        if(e instanceof BadCredentialsException){
            ajax.setMsg("密码错误");
        }else if( e instanceof CredentialsExpiredException){
            ajax.setMsg("密码过期");
        } else if (e instanceof DisabledException) {
            ajax.setMsg("账号不可用");
        } else if (e instanceof LockedException) {
            ajax.setMsg("账号锁定");
        } else if (e instanceof InternalAuthenticationServiceException){
            ajax.setMsg("用户不存在");
        } else {
            ajax.setMsg("other error");
        }

        String s = new ObjectMapper().writeValueAsString(ajax);

        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.getWriter().print(s);
    }
}
