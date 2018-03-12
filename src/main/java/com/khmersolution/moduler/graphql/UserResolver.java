package com.khmersolution.moduler.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;

import java.util.Optional;

/**
 * Created by Vannaravuth Yo
 * Date : 3/8/2018, 5:09 PM
 * Email : ravuthz@gmail.com
 */

public class UserResolver implements GraphQLResolver<User> {

    private final UserRepository userRepository;

    public UserResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }
}