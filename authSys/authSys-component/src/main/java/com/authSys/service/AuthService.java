package com.authSys.service;

import com.authSys.entity.AuthEntity;
import com.authSys.entity.RoleEntity;
import com.authSys.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthMapper authMapper;

    public List<AuthEntity> getAllAuths(){
        return authMapper.selectAll();
    }

    public int insertAuth(AuthEntity auth){
        return authMapper.insertAuth(auth);
    }

    public int deleteById(Integer id){
        return authMapper.deleteById(id);
    }
}
