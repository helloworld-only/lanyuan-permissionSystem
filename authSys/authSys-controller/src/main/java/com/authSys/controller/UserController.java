package com.authSys.controller;

import com.authSys.domain.ResponseResult;
import com.authSys.entity.UserEntity;
import com.authSys.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("")
    @ResponseBody
    public ResponseResult getAllUsers(){
        List<UserEntity> allUsers = userService.getAllUsers();
        return ResponseResult.success(allUsers);
    }

    @RequestMapping("/add")
    public ResponseResult addUser(UserEntity userEntity){
        int i = userService.saveUser(userEntity);
        return ResponseResult.success(userEntity);
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    public UserEntity getUserEntityById(@PathVariable("id") Integer id){
        UserEntity user = userService.getById(id);
        return user;
    }

    @RequestMapping("/delete/{id}")
    public ResponseResult deleteUserById(@PathVariable("id") Integer id){
        int i = userService.removeById(id);
        return ResponseResult.success("删除成功");
    }

    @RequestMapping("/update")
    public ResponseResult updateUserInfo(UserEntity userEntity){
        int i = userService.updateUser(userEntity);
        return ResponseResult.success("更新成功");
    }
}
