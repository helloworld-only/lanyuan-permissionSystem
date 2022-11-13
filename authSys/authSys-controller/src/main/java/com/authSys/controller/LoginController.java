package com.authSys.controller;

import com.authSys.domain.Constants;
import com.authSys.domain.ResponseResult;
import com.authSys.entity.UserEntity;
import com.authSys.security.SysUser;
import com.authSys.service.UserService;
import com.authSys.utils.JwtUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/home")
    @ResponseBody
    public ResponseResult loginCheck(UserEntity userEntity, HttpServletRequest req){
        SysUser sysUser = (SysUser) req.getAttribute("sysUser");

        ResponseResult ajax = new ResponseResult();

        if(sysUser == null){
            ajax.setCode(400);
            ajax.setMsg("用户不存在");
        }else{
            UserEntity userEntity1 = sysUser.getUserEntity();
            String passwd = userEntity.getPasswd();
            String passwd1 = userEntity1.getPasswd();
            if (!passwd1.equals(passwd)){
                ajax.setCode(400);
                ajax.setMsg("用户名或密码错误");
            }else{
                ajax.setCode(200);
                ajax.setMsg("登录成功");
                String token = JwtUtil.getToken(sysUser);
                ajax.put(Constants.TOKEN,token);
            }
        }
        return ajax;
    }



    public ResponseResult loginCheck(UserEntity user, String code, String token){
        String captchaResult = null;
        try{
            captchaResult = JwtUtil.getCaptchaResult(token);
        }catch (ExpiredJwtException e){
            ResponseResult.fail("验证码过期");
        }catch(SignatureException e){
            ResponseResult.fail("非法操作");
        }

        if(!code.equals(captchaResult)){
            return ResponseResult.fail("验证码错误");
        }

        UserEntity userEntity = userService.getByAcctAndPw(user);
        if(userEntity == null){
            return ResponseResult.fail("用户名或密码错误");
        }

        ResponseResult success = ResponseResult.success("登录成功");
        String token1 = JwtUtil.getToken(userEntity); // 生成登录成功后的token
        success.put(Constants.TOKEN,token1);
        return success;
    }


    //UserEntity user, String code, HttpSession session
//    @PostMapping("/home")
//    @ResponseBody
    public ResponseResult loginCheck(HttpServletRequest req, @RequestBody String data){
        System.out.println(data);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = null;
        String acct = "";
        String passwd = "";
        String code = "";
        try {
            map = objectMapper.readValue(data, new TypeReference<Map>() {
            });

            acct = (String) map.get("acct");
            passwd = (String) map.get("passwd");
            code = (String) map.get("code");
        } catch (IOException e) {
            return ResponseResult.fail("系统出错");
        }
//        String captchaResult = (String) session.getAttribute("captchaResult");

        UserEntity user = new UserEntity();
        user.setAcct(acct);
        user.setPasswd(passwd);

        String captchaResult = "12";

        System.out.println(captchaResult);

        String msg = "";
        if(!captchaResult.equals(code)){
            return ResponseResult.fail("验证码错误");
        }else{
            UserEntity userEntity = userService.getByAcctAndPw(user);
            if(userEntity == null){
                return ResponseResult.fail("用户名或密码错误");
            }
        }
        return ResponseResult.success("登录成功");
    }



}
