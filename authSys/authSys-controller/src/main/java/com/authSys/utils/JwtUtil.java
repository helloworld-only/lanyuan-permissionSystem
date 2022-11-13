package com.authSys.utils;

import com.authSys.entity.UserEntity;
import com.authSys.security.SysUser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.security.Key;
import java.util.*;

public class JwtUtil {

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static long captchaTTL = 1000 * 60 * 40; // 单位：毫秒

    private static long loginTTL = 1000 * 60 * 40;


    public static String getToken(String captchaResult){
        String token = "";

        JwtBuilder builder = Jwts.builder();

        builder.setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + captchaTTL))
                .claim("captchaResult", captchaResult)
                .signWith(key);

        token = builder.compact();

        return token;
    }

    public static String getToken(UserEntity userEntity){
        String token = "";

        JwtBuilder builder = Jwts.builder();

        Integer userId = userEntity.getUserId();
        String acct = userEntity.getAcct();
        String passwd = userEntity.getPasswd();
        String userName = userEntity.getUserName();

        builder.setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + loginTTL))
                .claim("userId", userId)
                .claim("acct", acct)
                .claim("passwd",passwd)
                .claim("userName",userName)
//                .claim("userEntity",userEntity)
                .signWith(key);

        token = builder.compact();

        return token;
    }

    public static String getToken(SysUser sysUser){
        String token = "";

        UserEntity userEntity = sysUser.getUserEntity();
        Integer userId = userEntity.getUserId();
        String acct = userEntity.getAcct();
        String passwd = userEntity.getPasswd();
        String userName = userEntity.getUserName();

        Collection<GrantedAuthority> authorities = sysUser.getAuthorities();

        JwtBuilder builder = Jwts.builder();
        JwtBuilder jwtBuilder = builder.setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + loginTTL))
                .claim("userId", userId)
                .claim("acct", acct)
                .claim("passwd",passwd)
                .claim("userName",userName)
                .claim("authorities", authorities.toString())
                .signWith(key);
        token = jwtBuilder.compact();

        return token;
    }

    protected static Claims parseToken(String token){
        JwtParser parser = Jwts.parser();
        Claims body = parser.setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    public static String getCaptchaResult(String token){
        Claims claims = parseToken(token);
        String captchaResult = (String) claims.get("captchaResult");
        return captchaResult;
    }

    public static UserEntity getUserEntity(String token){
        Claims claims = parseToken(token);
        Integer userId = (Integer) claims.get("userId");
        String acct = (String) claims.get("acct");
        String passwd = (String) claims.get("passwd");
        String userName = (String) claims.get("userName");

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setAcct(acct);
        userEntity.setPasswd(passwd);
        userEntity.setUserName(userName);


        return userEntity;
    }

    public static SysUser getSysUser(String token) throws IOException {
        Claims claims = parseToken(token);



        Integer userId = (Integer) claims.get("userId");
        String acct = (String) claims.get("acct");
        String passwd = (String) claims.get("passwd");
        String userName = (String) claims.get("userName");
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setAcct(acct);
        userEntity.setPasswd(passwd);
        userEntity.setUserName(userName);


        List<GrantedAuthority> authorities = new ArrayList<>();

        String obj = (String) claims.get("authorities");
        ObjectMapper objectMapper = new ObjectMapper();

        if(obj != null){
            // 如果obj为null，objectMapper的readValue会报错
            List<String> tmp = objectMapper.readValue(obj, List.class);
            for(String auth : tmp){
                authorities.add(new SimpleGrantedAuthority(auth));
            }
        }


        SysUser sysUser = new SysUser(userEntity, authorities);

        return sysUser;
    }

}
