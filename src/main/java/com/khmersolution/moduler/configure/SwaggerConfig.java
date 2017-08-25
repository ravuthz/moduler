package com.khmersolution.moduler.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Vannaravuth Yo
 * Date : 8/24/2017, 9:46 AM
 * Email : ravuthz@gmail.com
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String title = "User Management API";
    private String description = "Users Restful API with Spring Boot + Spring Data + Spring Rest.";
    private String version = "1.0.0";
    private String termOfServiceUrl = null;
    private String contactName = "ravuthz@gmail.com";
    private String license = "License of API";
    private String licenseUrl = "http://github.com/ravuthz/user-management-api";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(Package.REST))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                title,
                description,
                version,
                termOfServiceUrl,
                contactName,
                license,
                licenseUrl);
        return apiInfo;
    }

    private ApiInfo apiInformation() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .termsOfServiceUrl(termOfServiceUrl)
                .contact(contactName)
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }
}
