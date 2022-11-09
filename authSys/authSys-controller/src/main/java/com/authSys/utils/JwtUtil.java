package com.authSys.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static long captchaTTL = 60 * 1 * 1000; // 单位：毫秒

    private static long loginTTL = 60 * 30 * 1000;


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

    public static String getToken(String userId, String acct){
        String token = "";

        JwtBuilder builder = Jwts.builder();

        builder.setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + loginTTL))
                .claim("userId", userId)
                .claim("acct", acct)
                .signWith(key);

        token = builder.compact();

        return token;
    }

    public static Claims parseToken(String token){
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

}
