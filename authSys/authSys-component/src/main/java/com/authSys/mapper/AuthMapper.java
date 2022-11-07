package com.authSys.mapper;

import com.authSys.entity.AuthEntity;

import java.util.List;

public interface AuthMapper {

    int insertAuth(AuthEntity auth);

    int deleteById(Integer id);

    List<AuthEntity> selectAll();

    AuthEntity selectById(Integer id);

    List<AuthEntity> selectByIds(List<Integer> ids);
}
