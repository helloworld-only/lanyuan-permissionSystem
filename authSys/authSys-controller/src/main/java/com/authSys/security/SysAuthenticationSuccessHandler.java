package com.authSys.security;

import com.authSys.domain.Constants;
import com.authSys.domain.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 登录成功的处理器
//@Component
public class SysAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String token = httpServletRequest.getParameter(Constants.TOKEN);

        ResponseResult responseResult = new ResponseResult();

        responseResult.setCode(200);
        responseResult.setMsg("登录成功");


        String s = new ObjectMapper().writeValueAsString(responseResult);
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.getWriter().print(s);
    }
}
