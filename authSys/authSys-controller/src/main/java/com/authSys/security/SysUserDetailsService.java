package com.authSys.security;

import com.authSys.entity.UserEntity;
import com.authSys.entity.UserRoleEntity;
import com.authSys.service.AuthService;
import com.authSys.service.RoleService;
import com.authSys.service.UserRoleService;
import com.authSys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRoleService userRoleService;


    @Override
    public UserDetails loadUserByUsername(String acct) throws UsernameNotFoundException {

        // 1.根据账号查询数据库（账号唯一）
        UserEntity userEntity = userService.getByAcct(acct);


        Integer userId = userEntity.getUserId();


        // 2. 获取该用户所具有的权限
        String sql = "select auth_name from user_auth_view where user_id = ?";
        BeanPropertyRowMapper<String> mapper = new BeanPropertyRowMapper<>(String.class);
        List<String> auths = jdbcTemplate.query(sql, mapper, userId);

        System.out.println(auths);

        // 3. 获得该用户所具有的角色
        List<UserRoleEntity> userRoleEntities = userRoleService.getViewByUserId(userId);
        System.out.println(userRoleEntities);

        // 4. 将该用户具有的角色和权限统一封装在一个list中
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 4.1 封装权限
        for(String auth : auths){
            authorities.add(new SimpleGrantedAuthority(auth));
        }
        // 4.2 封装角色
        for(UserRoleEntity entity : userRoleEntities){
            String roleName = "ROLE_" + entity.getRoleName();
            authorities.add(new SimpleGrantedAuthority(roleName));
        }



        SysUser sysUser = new SysUser(userEntity, authorities);
        return sysUser;


    }
}
