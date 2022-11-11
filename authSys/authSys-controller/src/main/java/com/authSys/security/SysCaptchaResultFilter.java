package com.authSys.security;

import com.authSys.domain.Constants;
import com.authSys.domain.ResponseResult;
import com.authSys.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 检验验证码是否正确的过滤器
public class SysCaptchaResultFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        String doLoginURI = "/home";

        int flag = 1;
        ResponseResult ajax = new ResponseResult();
        if(doLoginURI.equals(requestURI)){ // 只拦截登录地址
            String code = httpServletRequest.getParameter("code");
            String token = httpServletRequest.getParameter(Constants.TOKEN);

            String captchaResult = null;

            try{
                captchaResult = JwtUtil.getCaptchaResult(token);
            }catch (ExpiredJwtException e){
                ajax.setCode(401);
                ajax.setMsg("验证码过期");
                flag = 0;
            }catch (SignatureException e){
                ajax.setCode(401);
                ajax.setMsg("非法操作");
                flag = 0;
            }

            if (!code.equals(captchaResult)){
                ajax.setCode(400);
                ajax.setMsg("验证码错误");
                flag = 0;
            }
        }

        if(flag == 1){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else{
            String s = new ObjectMapper().writeValueAsString(ajax);
            httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpServletResponse.getWriter().print(s);
        }
    }
}
