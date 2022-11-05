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
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/home/user/display")
    @ResponseBody
    public List<UserEntity> getAllUsers(){
        List<UserEntity> allUsers = userService.getAllUsers();
        return allUsers;
    }

    @RequestMapping("/user/{id}")
    @ResponseBody
    public UserEntity getUserEntityById(@PathVariable("id") Integer id){
        UserEntity user = userService.getById(id);
        return user;
    }
}
