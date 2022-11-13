package com.authSys.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.authSys.entity.UserAuthEntity;
import com.authSys.entity.UserEntity;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.Driver;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.util.*;

public class Client {



    public static void main(String[] args) throws SQLException, IOException {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        long expireTime = 10000;

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority("ROLE_MIN"));
        collection.add(new SimpleGrantedAuthority("ROLE_MAX"));

        System.out.println(collection);

        JwtBuilder builder = Jwts.builder();
        String compact = builder.setHeaderParam("type", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("collection",collection.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(key)
                .compact();

        System.out.println(compact);

        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(key).parseClaimsJws(compact);
        Claims body = claimsJws.getBody();

        String  auths = (String) body.get("collection");

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> list = objectMapper.readValue(auths, List.class);
        System.out.println(list);

    }



    public static void testJwt1(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        long expireTime = 10000;

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1);
        userEntity.setAcct("11111111");
        userEntity.setPasswd("123");
        userEntity.setUserName("admin");

        JwtBuilder builder = Jwts.builder();
        String compact = builder.setHeaderParam("type", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("userEntity",userEntity)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(key)
                .compact();

        System.out.println(compact);

//        try{
//            Thread.sleep(1000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(key).parseClaimsJws(compact);
        Claims body = claimsJws.getBody();

        String user = body.get("userEntity").toString();
        System.out.println(user);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserEntity userEntity1 = objectMapper.readValue(user, UserEntity.class);
            System.out.println(userEntity1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void testJwt(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        System.out.println("=============创建 JWT===========");
        Date now = new Date();
        JwtBuilder builder= Jwts.builder()
                .setId(UUID.randomUUID().toString()) // 载荷-标准中注册的声明
                .setSubject("admin") // 载荷-标准中注册的声明
                .setIssuedAt(now) // 载荷-标准中注册的声明，表示签发时间
                .claim("id", "123456") // 载荷-公共的声明
                .claim("name", "MoonlightL") // 载荷-公共的声明
                .claim("sex", "male") // 载荷-公共的声明
                .signWith(key); // 签证

        String jwt = builder.compact();
        System.out.println("生成的 jwt :" +jwt);

        System.out.println("=============解析 JWT===========");

        try {
            Jws<Claims> result = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
            // 以下步骤随实际情况而定，只要上一行代码执行不抛异常就证明 jwt 是有效的、合法的
            Claims body = result.getBody();

            System.out.println("载荷-标准中注册的声明 id:" + body.getId());
            System.out.println("载荷-标准中注册的声明 subject:" + body.getSubject());
            System.out.println("载荷-标准中注册的声明 issueAt:" + body.getIssuedAt());


            System.out.println("载荷-公共的声明的 id:" + result.getBody().get("id"));
            System.out.println("载荷-公共的声明的 name:" + result.getBody().get("name"));
            System.out.println("载荷-公共的声明的 sex:" + result.getBody().get("sex"));

        } catch (JwtException ex) { // jwt 不合法或过期都会抛异常
            ex.printStackTrace();
        }
    }
}
