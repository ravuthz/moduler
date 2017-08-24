package com.khmersolution.moduler.service;

import com.khmersolution.moduler.domain.Role;
import com.khmersolution.moduler.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vannaravuth Yo
 * Date : 8/25/17, 12:14 AM
 * Email : ravuthz@gmail.com
 */

public class CustomUserDetails extends User implements UserDetails {
    private static final long serialVersionUID = 1L;
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    private List<String> userRoles;

    public CustomUserDetails(User user) {
        super(user);

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
    }

    public CustomUserDetails(User user, List<String> userRoles) {
        super(user);
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
//        String roles = StringUtils.collectionToCommaDelimitedString(userRoles);
//        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
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
        return true;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

}
