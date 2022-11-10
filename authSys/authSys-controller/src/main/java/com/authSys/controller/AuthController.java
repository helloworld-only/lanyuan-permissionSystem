package com.authSys.controller;

import com.authSys.domain.ResponseResult;
import com.authSys.entity.AuthEntity;
import com.authSys.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping("")
    public ResponseResult getAllAuths(){
        List<AuthEntity> allAuths = authService.getAllAuths();
        return ResponseResult.success(allAuths);
    }

    @RequestMapping("/add")
    public ResponseResult addAuth(AuthEntity auth){
        int i = authService.insertAuth(auth);
        return ResponseResult.success(auth);
    }

    @RequestMapping("/delete/{id}")
    public ResponseResult deleteAuth(@PathVariable("id") Integer id){
        int i = authService.deleteById(id);
        return ResponseResult.success("删除成功");
    }
}
