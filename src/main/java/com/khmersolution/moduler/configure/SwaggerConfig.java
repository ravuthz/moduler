package com.khmersolution.moduler.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/24/2017, 9:46 AM
 * Email : ravuthz@gmail.com
 */

@Configuration
@EnableSwagger2
@Import({springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class SwaggerConfig {

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

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/rest/**"))
                .build()
                .globalOperationParameters(parameterList)
                .apiInfo(apiInfoBuilder());
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",
                "none",
                "method",
                "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,
                false,
                60000L);
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

    private Parameter parameterBuilder(String type, String name, String description, String defaultValue, ModelRef modelRef, boolean required) {
        return new ParameterBuilder()
                .name(name)
                .description(description)
                .defaultValue(defaultValue)
                .parameterType(type)
                .modelRef(modelRef)
                .required(required).build();
    }
}
