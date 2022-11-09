package com.authSys.controller;

import com.authSys.domain.ResponseResult;
import com.authSys.entity.RoleAuthEntity;
import com.authSys.mapper.RoleAuthMapper;
import com.authSys.service.RoleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home/role/{roleId}/authDistribution")
public class RoleAuthController {

    @Autowired
    private RoleAuthService roleAuthService;

    @RequestMapping("")
    public List<RoleAuthEntity> getAuthsByRoleId(@PathVariable("roleId") Integer roleId){
        List<RoleAuthEntity> list = roleAuthService.selectViewByRoleId(roleId);
        return list;
    }

    @RequestMapping("/delete/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id){
        int i = roleAuthService.deleteById(id);
        return ResponseResult.success("删除成功");
    }


    @RequestMapping("/add")
    public ResponseResult addRoleAuths(@PathVariable("roleId") Integer roleId,
                                       @RequestBody List<Integer> authIds){
        List<RoleAuthEntity> list = new ArrayList<>();
        for(Integer authId : authIds){
            RoleAuthEntity roleAuthEntity = new RoleAuthEntity();
            roleAuthEntity.setRoleId(roleId);
            roleAuthEntity.setAuthId(authId);
            list.add(roleAuthEntity);
        }
        int i = roleAuthService.insertRoleAuths(list);
        return ResponseResult.success(list);
    }
}
