package com.authSys.mapper;

import com.authSys.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserMapper {

    int insertUser(UserEntity userEntity);

//    int insertUser(@Param("acct") String acct, @Param("passwd") String passwd, @Param("userName") String userName);

//    int insertUser();

    int deleteById(Integer id);

    int updateUser(UserEntity userEntity);

    List<UserEntity> selectAll();

    UserEntity selectById(Integer id);


    UserEntity selectByAcctAndPw(@Param("acct") String acct, @Param("passwd") String passwd);

    List<UserEntity> selectByKeyName(String key);

    UserEntity selectByAcct(String acct);
}
