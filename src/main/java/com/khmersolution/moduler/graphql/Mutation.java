package com.khmersolution.moduler.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Vannaravuth Yo
 * Date : 3/8/2018, 5:39 PM
 * Email : ravuthz@gmail.com
 */

public class Mutation implements GraphQLMutationResolver {

    private final UserRepository userRepository;

    @Autowired
    public Mutation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User newUser() {
        return User.staticUser("graphql", "spring-boot");
    }

}
