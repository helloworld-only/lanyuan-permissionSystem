package com.authSys.mapper;

import com.authSys.entity.RoleAuthEntity;

import java.util.List;

public interface RoleAuthMapper {

    int insertRoleAuths(List<RoleAuthEntity> list);

    int deleteById(Integer id);

    int deleteByRoleId(Integer roleId);

    int deleteByAuthId(Integer authId);

    List<RoleAuthEntity> selectAll();

    List<RoleAuthEntity> selectViewByRoleId(Integer roleId);

    List<RoleAuthEntity> selectByRoleId(Integer roleId);

    List<RoleAuthEntity> selectByRoleIds(List<Integer> ids);
}
