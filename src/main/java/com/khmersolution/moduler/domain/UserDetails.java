package com.khmersolution.moduler.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/28/2017, 4:39 PM
 * Email : ravuthz@gmail.com
 */
public class UserDetails extends User implements org.springframework.security.core.userdetails.UserDetails {
    private static final long serialVersionUID = 1L;
    private List<String> userRoles = new ArrayList<>();

    public UserDetails(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roleList = this.getRoles();
        if (!roleList.isEmpty()) {
            roleList.forEach(role -> userRoles.add(role.getRole().toUpperCase()));
        }
        String roles = StringUtils.collectionToCommaDelimitedString(userRoles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
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
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
