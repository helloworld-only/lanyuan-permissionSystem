package com.authSys.security;

import com.authSys.domain.Constants;
import com.authSys.domain.ResponseResult;
import com.authSys.entity.UserEntity;
import com.authSys.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


// 也可以通过 BasicAuthenticationFilter实现
public class SysTokenAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    public SysTokenAuthenticationFilter(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

//    public SysTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        UsernamePasswordAuthenticationToken authentication = null;


            authentication = getAuthentication(request, response);


        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response){
        String token = request.getParameter(Constants.TOKEN);
        SysUser sysUser = null;
        String acct = "";
        String passwd = "";
        Collection<GrantedAuthority> authorities = null;


        if("/home".equals(request.getRequestURI())){
            // 进入这里说明是登录请求
            acct = request.getParameter("acct");
            passwd = request.getParameter("passwd");
            sysUser = (SysUser) userDetailsService.loadUserByUsername(acct);
            authorities = sysUser.getAuthorities();
            request.setAttribute("sysUser", sysUser);
        }else{
            // 进入这里说明是非登录请求，会携带token。
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
