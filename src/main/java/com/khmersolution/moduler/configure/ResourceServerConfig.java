package com.khmersolution.moduler.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by Vannaravuth Yo
 * Date : 8/28/17, 11:18 PM
 * Email : ravuthz@gmail.com
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/", "/home", "/register", "/login").permitAll()
                .antMatchers("/**").authenticated();
    }
}