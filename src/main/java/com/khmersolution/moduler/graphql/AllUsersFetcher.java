package com.khmersolution.moduler.graphql;

import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 3/9/2018, 10:15 AM
 * Email : ravuthz@gmail.com
 */

@Component
public class AllUsersFetcher implements DataFetcher<List<User>> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return (List<User>) userRepository.findAll();
    }
}