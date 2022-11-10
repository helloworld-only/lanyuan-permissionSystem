package com.authSys.controller;

import com.authSys.domain.ResponseResult;
import com.authSys.entity.RoleEntity;
import com.authSys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("")
    public ResponseResult getAllRoles(){
        List<RoleEntity> allRoles = roleService.getAllRoles();
        return ResponseResult.success(allRoles);
    }

    @RequestMapping("/delete/{id}")
    public ResponseResult deleteRoleById(@PathVariable("id") Integer id){
        int i = roleService.deleteById(id);
        return ResponseResult.success("删除成功");
    }

    @RequestMapping("/add")
    public ResponseResult addRole(RoleEntity role){
        int i = roleService.insertRole(role);
        return ResponseResult.success(role);
    }

}
