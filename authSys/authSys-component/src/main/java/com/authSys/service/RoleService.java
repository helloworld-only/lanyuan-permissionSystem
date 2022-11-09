package com.authSys.service;

import com.authSys.entity.RoleEntity;
import com.authSys.mapper.RoleAuthMapper;
import com.authSys.mapper.RoleMapper;
import com.authSys.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleAuthMapper roleAuthMapper;

    public List<RoleEntity> getAllRoles(){
        return roleMapper.selectAll();
    }

    public int insertRole(RoleEntity role){
        return roleMapper.insertRole(role);
    }

    @Transactional
    public int deleteById(Integer id){
        int i = roleAuthMapper.deleteByRoleId(id); // 先删除 角色-权限表 中涉及该角色的行
        int i1 = userRoleMapper.deleteByRoleId(id);// 再删除 用户-角色表 中涉及该角色的行
        int affectedRows = roleMapper.deleteById(id);// 最后删除该角色
        return affectedRows;
    }
}
