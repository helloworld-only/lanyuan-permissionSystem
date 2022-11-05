package com.authSys.mapper;

import com.authSys.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleMapper {

    int insertOne(UserRoleEntity userRoleEntity);

    int deleteById(Integer id);

    List<UserRoleEntity> selectAll();

    List<UserRoleEntity> selectByUserId(Integer userId);

    List<UserRoleEntity> selectByUserIds(List<Integer> ids);
}
