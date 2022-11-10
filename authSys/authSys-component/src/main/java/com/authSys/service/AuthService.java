package com.authSys.service;

import com.authSys.entity.AuthEntity;
import com.authSys.entity.RoleEntity;
import com.authSys.mapper.AuthMapper;
import com.authSys.mapper.RoleAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private RoleAuthMapper roleAuthMapper;

    public List<AuthEntity> getAllAuths(){
        return authMapper.selectAll();
    }

    public int insertAuth(AuthEntity auth){
        return authMapper.insertAuth(auth);
    }

    @Transactional
    public int deleteById(Integer id){
        roleAuthMapper.deleteByAuthId(id);
        return authMapper.deleteById(id);
    }
}
