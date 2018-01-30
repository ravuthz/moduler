package com.khmersolution.moduler.configure;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

import static com.google.common.base.Predicates.or;

/**
 * Created by Vannaravuth Yo
 * Date : 8/24/2017, 9:46 AM
 * Email : ravuthz@gmail.com
 */

@Configuration
@EnableSwagger2
@Import({springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class SwaggerConfig {

    public static final String AUTHORIZATION = "AUTHORIZATION";

    @Value("${security.oauth2.url}")
    private String oAuthTokenUrl;

    @Value("${swagger.apiInfo.title:}")
    private String title;

    @Value("${swagger.apiInfo.description:}")
    private String description;

    @Value("${swagger.apiInfo.version:}")
    private String version;

    @Value("${swagger.apiInfo.termOfServiceUrl:}")
    private String termOfServiceUrl;

    @Value("${swagger.apiInfo.contactUrl:}")
    private String contactUrl;

    @Value("${swagger.apiInfo.contactName:}")
    private String contactName;

    @Value("${swagger.apiInfo.contactEmail:}")
    private String contactEmail;

    @Value("${swagger.apiInfo.license:}")
    private String license;

    @Value("${swagger.apiInfo.licenseUrl:}")
    private String licenseUrl;

    @Value("${swagger.defaultKey.page:page}")
    private String pageKey;

    private String pageDescription = "Pagination's page number start by index";

    @Value("${swagger.defaultKey.size:size}")
    private String sizeKey;

    private String sizeDescription = "Pagination's items per page";

    @Value("${swagger.defaultKey.sort:sort}")
    private String sortKey;

    private String sortDescription = "Pagination's sort item by field";

    @Value("${swagger.defaultValue.page:0}")
    private String pageValue;

    @Value("${swagger.defaultValue.size:20}")
    private String sizeValue;

    @Value("${swagger.defaultValue.sort:id,desc}")
    private String sortValue;

    @Bean
    public Docket api() {
        ModelRef intModel = new ModelRef("integer");

        List<Parameter> parameterList = Arrays.asList(
                parameterBuilder("query", pageKey, pageDescription, pageValue, intModel, false),
                parameterBuilder("query", sizeKey, sizeDescription, sizeValue, intModel, false),
                parameterBuilder("query", sortKey, sortDescription, sortValue, intModel, false)
        );

        Set<String> produces = new HashSet<>();
        produces.add(MediaType.APPLICATION_JSON_VALUE);
        produces.add(MediaType.APPLICATION_ATOM_XML_VALUE);
        produces.add("application/hal+json");
        produces.add("text/uri-list");
        produces.add("application/x-spring-data-compact+json");

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(
                        or(
                                RequestHandlerSelectors.withClassAnnotation(Repository.class),
                                RequestHandlerSelectors.withClassAnnotation(RestController.class),
                                RequestHandlerSelectors.withClassAnnotation(BasePathAwareController.class),
                                RequestHandlerSelectors.withClassAnnotation(RepositoryRestController.class)
//                                RequestHandlerSelectors.basePackage("org.springframework.security.oauth2.provider.endpoint")
                        )
                )
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(parameterList)
                .produces(produces)
                .apiInfo(apiInfoBuilder())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Collections.singletonList(securitySchema()));
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                null,
                "none",
                "alpha",
                "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,
                false,
                60000L);
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                null,
                null,
                null,
                null,
                null,
                ApiKeyVehicle.HEADER,
                AUTHORIZATION,
                ","
        );
    }

    private ApiInfo apiInfoBuilder() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .termsOfServiceUrl(termOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }

    private Parameter parameterBuilder(String type, String name, String description, String defaultValue,
                                       ModelRef modelRef, boolean required) {
        return new ParameterBuilder()
                .name(name)
                .description(description)
                .defaultValue(defaultValue)
                .parameterType(type)
                .modelRef(modelRef)
                .required(required).build();
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> list = new ArrayList();
        list.add(new AuthorizationScope(AuthorizationConfig.SCOPE_READ, AuthorizationConfig.SCOPE_READ_DESC));
        list.add(new AuthorizationScope(AuthorizationConfig.SCOPE_WRITE, AuthorizationConfig.SCOPE_WRITE_DESC));
        return list;
    }

    private List<GrantType> grantTypes() {
        ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant =
                new ResourceOwnerPasswordCredentialsGrant(oAuthTokenUrl);
        List<GrantType> grantTypes = Collections.singletonList(resourceOwnerPasswordCredentialsGrant);
        return grantTypes;
    }

    private OAuth securitySchema() {
        return new OAuth(AUTHORIZATION, scopes(), grantTypes());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope(AuthorizationConfig.SCOPE_READ, AuthorizationConfig.SCOPE_READ_DESC),
                new AuthorizationScope(AuthorizationConfig.SCOPE_WRITE, AuthorizationConfig.SCOPE_WRITE_DESC)
        };
        return Lists.newArrayList(new SecurityReference(AUTHORIZATION, authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

}
