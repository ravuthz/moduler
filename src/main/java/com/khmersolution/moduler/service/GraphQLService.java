package com.khmersolution.moduler.service;

import com.khmersolution.moduler.graphql.AllUsersFetcher;
import com.khmersolution.moduler.repository.UserRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Created by Vannaravuth Yo
 * Date : 3/9/2018, 10:07 AM
 * Email : ravuthz@gmail.com
 */

@Service
public class GraphQLService {

    private GraphQL graphQL;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AllUsersFetcher allUsersFetcher;


    @Value("classpath:users.graphql")
    Resource resource;

    @PostConstruct
    public void loadSchema() throws IOException {
        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allUsers", allUsersFetcher))

                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}
