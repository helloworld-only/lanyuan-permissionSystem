package com.authSys.service;

import com.authSys.entity.UserRoleEntity;
import com.authSys.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public int saveUserRoles(List<UserRoleEntity> list){
        return userRoleMapper.insertUserRoles(list);
    }

    public int removeById(Integer id){
        return userRoleMapper.deleteById(id);
    }

    public int removeByUserId(Integer userId){
        return userRoleMapper.deleteByUserId(userId);
    }

    public List<UserRoleEntity> getByUserId(Integer userId){
        return userRoleMapper.selectByUserId(userId);
    }

    public List<UserRoleEntity> getViewByUserId(Integer userId){
        return userRoleMapper.selectViewByUserId(userId);
    }

    public List<UserRoleEntity> getByUserIds(List<Integer> ids){
        return userRoleMapper.selectByUserIds(ids);
    }

}
