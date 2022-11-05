package com.authSys.mapper;

import com.authSys.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserMapper {

    int insertUser(UserEntity user);

    int deleteById(Integer id);

    int updateUser(UserEntity user);

    List<UserEntity> selectAll();

    UserEntity selectById(Integer id);

    UserEntity selectByAcctAndPw(@Param("acct") String acct, @Param("passwd") String passwd);

    List<UserEntity> selectByKeyName(String key);

}
