package com.authSys.controller;

import com.authSys.domain.ResponseResult;
import com.authSys.entity.UserRoleEntity;
import com.authSys.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home/user/{userId}/roleDistribution")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("")
    public List<UserRoleEntity> getRolesByUserId(@PathVariable("userId") Integer userId){
        List<UserRoleEntity> userAllRoles = userRoleService.getViewByUserId(userId);
        return userAllRoles;
    }

    @RequestMapping("/delete/{id}")
    public ResponseResult deleteUserRoleById(@PathVariable("id") Integer id){
        int i = userRoleService.removeById(id);
        return ResponseResult.success("删除成功");
    }

    @RequestMapping("/add")
    public ResponseResult addUserRoles(@PathVariable("userId") Integer userId,
                                      @RequestBody List<Integer> roleIds){
        List<UserRoleEntity> list = new ArrayList<>();
        for(Integer roleId: roleIds){
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            list.add(userRoleEntity);
        }
        userRoleService.saveUserRoles(list);
        return ResponseResult.success(list);
    }

}
