package com.authSys.security;

import com.authSys.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SysUser extends User {

    private UserEntity userEntity;


    public SysUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities){
        super(userEntity.getAcct(), "{noop}" + userEntity.getPasswd(), authorities);
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity(){
        return userEntity;
    }
}
