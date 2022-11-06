package com.authSys.controller;

import com.authSys.domain.ResponseResult;
import com.authSys.entity.UserEntity;
import com.authSys.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    //UserEntity user, String code, HttpSession session
//    @PostMapping("/home")
//    @ResponseBody
//    public ResponseResult loginCheck(HttpServletRequest req, @RequestBody String data){
//        System.out.println(data);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String,Object> map = null;
//        String acct = "";
//        String passwd = "";
//        String code = "";
//        try {
//            map = objectMapper.readValue(data, new TypeReference<Map>() {
//            });
//
//            acct = (String) map.get("acct");
//            passwd = (String) map.get("passwd");
//            code = (String) map.get("code");
//        } catch (IOException e) {
//            return ResponseResult.fail("系统出错");
//        }
////        String captchaResult = (String) session.getAttribute("captchaResult");
//
//        UserEntity user = new UserEntity();
//        user.setAcct(acct);
//        user.setPasswd(passwd);
//
//        String captchaResult = "12";
//
//        System.out.println(captchaResult);
//
//        String msg = "";
//        if(!captchaResult.equals(code)){
//            return ResponseResult.fail("验证码错误");
//        }else{
//            UserEntity userEntity = userService.getByAcctAndPw(user);
//            if(userEntity == null){
//                return ResponseResult.fail("用户名或密码错误");
//            }
//        }
//        return ResponseResult.success("登录成功");
//    }

    @PostMapping("/home")
    @ResponseBody
    public ResponseResult loginCheck(UserEntity user, String code){

        System.out.println(user);
        System.out.println(code);
        return ResponseResult.success("登录成功");
    }
}
