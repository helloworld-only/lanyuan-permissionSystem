package com.authSys.interceptors;

import com.authSys.domain.Constants;
import com.authSys.entity.UserEntity;
import com.authSys.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截请求：" + request.getRequestURI());

        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL);

        String origin = request.getHeader("Origin");
        System.out.println("Origin = " + origin);
        String referer = request.getHeader("Referer");
        System.out.println("Referer = " + referer);


        String token = request.getParameter(Constants.TOKEN);
        UserEntity userEntity = null;


        try{
            userEntity = JwtUtil.getUserEntity(token);
            System.out.println(userEntity);
        }catch(ExpiredJwtException e){
            response.sendRedirect(origin);
            return false;
        }catch(SignatureException e){
            response.sendRedirect(origin);
            return false;
        }

        if(userEntity == null){
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        return;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        return;
    }
}
