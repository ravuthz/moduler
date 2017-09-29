package com.khmersolution.moduler.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vannaravuth Yo
 * Date : 8/28/2017, 4:39 PM
 * Email : ravuthz@gmail.com
 */

@Slf4j
public class Account extends User implements org.springframework.security.core.userdetails.UserDetails {
    private static final long serialVersionUID = 1L;

    public Account(User user) {
        super(user);
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName().toUpperCase()));
            }
        }
        return authorities;
    }
}
