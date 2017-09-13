package com.khmersolution.moduler.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","PUT", "POST", "PATCH", "DELETE")
                .allowedHeaders("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
//                .exposedHeaders("header1", "header2")
//                .allowCredentials(false)
                .maxAge(3600);
    }
}
