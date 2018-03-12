package com.khmersolution.moduler.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 3/8/2018, 4:47 PM
 * Email : ravuthz@gmail.com
 */

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private final UserRepository userRepository;

    public Query(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public long countUsers() {
        return userRepository.count();
    }
}
