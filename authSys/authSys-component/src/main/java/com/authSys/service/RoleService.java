package com.authSys.service;

import com.authSys.entity.RoleEntity;
import com.authSys.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<RoleEntity> getAllRoles(){
        return roleMapper.selectAll();
    }

    public int insertRole(RoleEntity role){
        return roleMapper.insertRole(role);
    }

    public int deleteById(Integer id){
        return roleMapper.deleteById(id);
    }
}
