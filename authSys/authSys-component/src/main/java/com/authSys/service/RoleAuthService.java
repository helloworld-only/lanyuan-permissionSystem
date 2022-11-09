package com.authSys.service;

import com.authSys.entity.RoleAuthEntity;
import com.authSys.mapper.RoleAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleAuthService {

    @Autowired
    private RoleAuthMapper roleAuthMapper;

    public int insertRoleAuths(List<RoleAuthEntity> list){
        return roleAuthMapper.insertRoleAuths(list);
    }

    public int deleteById(Integer id){
        return roleAuthMapper.deleteById(id);
    }

    public int deleteByRoleId(Integer roleId){
        return roleAuthMapper.deleteByRoleId(roleId);
    }

    public List<RoleAuthEntity> selectViewByRoleId(Integer roleId){
        return roleAuthMapper.selectViewByRoleId(roleId);
    }


}
