package com.authSys.security;

import com.authSys.domain.Constants;
import com.authSys.domain.ResponseResult;
import com.authSys.entity.UserEntity;
import com.authSys.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;


// UsernamePasswordAuthenticationFilter

// 获取用户名和密码并交给权限管理器验证
// 自定义登录拦截器
// 要注意添加至容器时，要提供 AuthenticationManager（必要）、AuthenticationSuccessHandler、AuthenticationFailureHandler
//

public class SysUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

//    UsernamePasswordAuthenticationFilter
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER
            = new AntPathRequestMatcher("/home","POST"); // 登录的url

    private  boolean postOnly = true;

    public SysUsernamePasswordAuthenticationFilter(){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }


    protected SysUsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("sysUsernamePasswordAuthenticationFilter");
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
            AuthenticationManager authenticationManager = this.getAuthenticationManager();
            return authenticationManager.authenticate(authentication);
        }

    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response){
        String token = request.getParameter(Constants.TOKEN);
        SysUser sysUser = null;
        String acct = "";
        String passwd = "";
        Collection<GrantedAuthority> authorities = null;

        if("/home".equals(request.getRequestURI())){
            acct = request.getParameter("acct");
            passwd = request.getParameter("passwd");
        }else{
            if(token != null){
                try{
                    sysUser = JwtUtil.getSysUser(token);
                }catch (Exception e){
                    e.printStackTrace();
                }
//            catch (ExpiredJwtException e){
//
//            }catch (SignatureException e){
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

                if(sysUser != null){
                    UserEntity userEntity = sysUser.getUserEntity();
                    acct = userEntity.getAcct();
                    passwd = userEntity.getPasswd();
                    authorities = sysUser.getAuthorities();
                }
            }
        }



        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(acct, passwd, authorities);

        return authentication;
    }

}
