package com.authSys.test;

import com.authSys.entity.UserEntity;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.util.*;

public class Client {



    public static void main(String[] args) throws SQLException, IOException {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        long expireTime = 10000;

        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(new SimpleGrantedAuthority("ROLE_MIN"));
        collections.add(new SimpleGrantedAuthority("ROLE_MAX"));
//        Collection<String> collection = new ArrayList<>();
//        collection.add("ROLE_MIN");
//        collection.add("ROLE_MAX");
        List<String> strings = new ArrayList<>();
        for (GrantedAuthority sga : collections){
            String authority = sga.getAuthority();
            strings.add(authority);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String collection = objectMapper.writeValueAsString(strings);


        System.out.println(strings);

        JwtBuilder builder = Jwts.builder();
        String compact = builder.setHeaderParam("type", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("collection",strings)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(key)
                .compact();

        System.out.println(compact);

        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(key).parseClaimsJws(compact);
        Claims body = claimsJws.getBody();

        List<String> list = (List<String>) body.get("collection");
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

        System.out.println("=============?????? JWT===========");
        Date now = new Date();
        JwtBuilder builder= Jwts.builder()
                .setId(UUID.randomUUID().toString()) // ??????-????????????????????????
                .setSubject("admin") // ??????-????????????????????????
                .setIssuedAt(now) // ??????-?????????????????????????????????????????????
                .claim("id", "123456") // ??????-???????????????
                .claim("name", "MoonlightL") // ??????-???????????????
                .claim("sex", "male") // ??????-???????????????
                .signWith(key); // ??????

        String jwt = builder.compact();
        System.out.println("????????? jwt :" +jwt);

        System.out.println("=============?????? JWT===========");

        try {
            Jws<Claims> result = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
            // ???????????????????????????????????????????????????????????????????????????????????? jwt ????????????????????????
            Claims body = result.getBody();

            System.out.println("??????-???????????????????????? id:" + body.getId());
            System.out.println("??????-???????????????????????? subject:" + body.getSubject());
            System.out.println("??????-???????????????????????? issueAt:" + body.getIssuedAt());


            System.out.println("??????-?????????????????? id:" + result.getBody().get("id"));
            System.out.println("??????-?????????????????? name:" + result.getBody().get("name"));
            System.out.println("??????-?????????????????? sex:" + result.getBody().get("sex"));

        } catch (JwtException ex) { // jwt ?????????????????????????????????
            ex.printStackTrace();
        }
    }
}
