package com.authSys.security;

import com.authSys.domain.Constants;
import com.authSys.domain.ResponseResult;
import com.authSys.entity.UserEntity;
import com.authSys.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 也可以通过 BasicAuthenticationFilter实现
public class SysTokenAuthenticationFilter extends OncePerRequestFilter {

//    public SysTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getParameter(Constants.TOKEN);
        if (token == null){
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        if (authentication != null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token){
        UserEntity userEntity = null;

        try{
            userEntity = JwtUtil.getUserEntity(token);
        }catch (Exception e){
            return null;
        }

        String acct = userEntity.getAcct();
        String passwd = userEntity.getPasswd();

        if(acct != null && !acct.equals("")){
            return new UsernamePasswordAuthenticationToken(acct, passwd);
        }
        return null;
    }
}
