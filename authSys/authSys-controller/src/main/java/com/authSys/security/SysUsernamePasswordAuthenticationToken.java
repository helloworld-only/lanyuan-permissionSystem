package com.authSys.security;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;

public class SysUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public SysUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
