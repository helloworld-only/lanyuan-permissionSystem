package com.authSys.service;

import com.authSys.entity.RoleEntity;
import com.authSys.entity.UserEntity;
import com.authSys.mapper.UserMapper;
import com.authSys.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Transactional
    public int saveUser(UserEntity userEntity){
        int affectedRow = userMapper.insertUser(userEntity);
        return affectedRow;
    }

    @Transactional
    public int removeById(Integer id){
        int i = userRoleMapper.deleteByUserId(id); // 先删除 用户-角色表 中涉及该用户的行
        int affectedRows = userMapper.deleteById(id); // 最后删除该用户
        return affectedRows;
    }

    public int updateUser(UserEntity userEntity){
        int affectedRows = userMapper.updateUser(userEntity);
        return affectedRows;
    }

    public List<UserEntity> getAllUsers(){
        List<UserEntity> users = userMapper.selectAll();
        return users;
    }

    public UserEntity getById(Integer id){
        UserEntity user = userMapper.selectById(id);
        return user;
    }

    public UserEntity getByAcct(String acct){
        UserEntity userEntity = userMapper.selectByAcct(acct);
        return userEntity;
    }

    public UserEntity getByAcctAndPw(UserEntity user){
        String acct = user.getAcct();
        String passwd = user.getPasswd();
        UserEntity userEntity = userMapper.selectByAcctAndPw(acct, passwd);
        return userEntity;
    }

    public List<UserEntity> getByKeyWord(String key){
        String newKey = "%" + key + "%";
        List<UserEntity> users = userMapper.selectByKeyName(newKey);
        return users;
    }

    public List<RoleEntity> getRoles(Integer id){
        return null;
    }

}
