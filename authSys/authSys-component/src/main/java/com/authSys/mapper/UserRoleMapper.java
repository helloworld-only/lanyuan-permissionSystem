package com.authSys.mapper;

import com.authSys.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleMapper {

    int insertUserRoles(List<UserRoleEntity> list);

    int deleteById(Integer id);

    int deleteByUserId(Integer userId);

    List<UserRoleEntity> selectAll();

    List<UserRoleEntity> selectByUserId(Integer userId);

    List<UserRoleEntity> selectViewByUserId(Integer userId);

    List<UserRoleEntity> selectByUserIds(List<Integer> ids);
}
