package com.khmersolution.moduler.service;

import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Vannaravuth Yo
 * Date : 8/28/2017, 5:11 PM
 * Email : ravuthz@gmail.com
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        }
        return userRepository.findByUsername(username);
    }

}