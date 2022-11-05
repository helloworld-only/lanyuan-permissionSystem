package com.authSys.mapper;

import com.authSys.entity.RoleEntity;
import com.authSys.entity.UserEntity;

import java.util.List;

public interface RoleMapper {

    int insertRole(RoleEntity role);

    int deleteById(Integer id);

    int updateRole(RoleEntity role);

    List<RoleEntity> selectAll();

    RoleEntity selectById(Integer id);

    List<RoleEntity> selectByUserId(Integer userId);

    //
    List<RoleEntity> selectByIds(List<Integer> ids);

}
