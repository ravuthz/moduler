package com.khmersolution.moduler.configure;

import com.khmersolution.moduler.graphql.GraphQLErrorAdapter;
import com.khmersolution.moduler.graphql.Mutation;
import com.khmersolution.moduler.graphql.Query;
import com.khmersolution.moduler.graphql.UserResolver;
import com.khmersolution.moduler.repository.UserRepository;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vannaravuth Yo
 * Date : 3/8/2018, 5:13 PM
 * Email : ravuthz@gmail.com
 */

//@Configuration
public class GraphqlConfiguration {

    @Autowired
    private UserRepository userRepository;

    public GraphqlConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
                List<GraphQLError> clientErrors = errors.stream().filter(this::isClientError).collect(Collectors.toList());
                List<GraphQLError> serverErrors = errors.stream().filter(e -> !isClientError(e)).map(GraphQLErrorAdapter::new).collect(Collectors.toList());
                List<GraphQLError> e = new ArrayList<>();
                e.addAll(clientErrors);
                e.addAll(serverErrors);
                return e;
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };
    }

    @Bean
    public UserResolver userResolver(UserRepository userRepository) {
        return new UserResolver(userRepository);
    }

    @Bean
    public Query query(UserRepository userRepository) {
        return new Query(userRepository);
    }

    @Bean
    public Mutation mutation(UserRepository userRepository) {
        return new Mutation(userRepository);
    }
}