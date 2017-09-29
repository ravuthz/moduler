package com.khmersolution.moduler.service;

import com.khmersolution.moduler.domain.Account;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Vannaravuth Yo
 * Date : 8/28/2017, 5:11 PM
 * Email : ravuthz@gmail.com
 */

@Slf4j
@Service(value = "UserDetailsService")
public class AccountService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final String message = "Username or Password is incorrect or User is disable, with username: ";

    @Autowired
    private UserRepository userRepository;

    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;

        if (username.contains("@")) {
            user = userRepository.findByEmailAndEnabledTrue(username);
        } else {
            user = userRepository.findByUsernameAndEnabledTrue(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException(message + username);
        }

        return new Account(user);
    }

}