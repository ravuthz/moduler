package com.khmersolution.moduler.service;

import com.khmersolution.moduler.domain.Permission;
import com.khmersolution.moduler.domain.Role;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.RoleRepository;
import com.khmersolution.moduler.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/28/2017, 5:11 PM
 * Email : ravuthz@gmail.com
 */

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        List<Role> roles = user.getRoles();
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(roles));

    }

    private List<String> getPermissions(Collection<Role> roles) {
        List<String> permission = new ArrayList<>();
        List<Permission> permissionList = new ArrayList<>();
        for (Role role : roles) {
            permissionList.addAll(role.getPermissions());
        }
        for (Permission item : permissionList) {
            permission.add(item.getName());
        }
        return permission;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        log.debug("Permissions: \n" + authorities.toString());
        return authorities;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPermissions(roles));
    }
}