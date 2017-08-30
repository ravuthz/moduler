package com.khmersolution.moduler.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Vannaravuth Yo
 * Date : 8/29/17, 9:52 PM
 * Email : ravuthz@gmail.com
 */

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resourceId}")
    private String resourceId;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenServices;

    // To allow the rResourceServerConfigurerAdapter to understand the token,
    // it must share the same characteristics with AuthorizationServerConfigurerAdapter.
    // So, we must wire it up the beans in the ResourceServerSecurityConfigurer.
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(resourceId)
                .tokenServices(tokenServices)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatcher(new OAuthRequestedMatcher())
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests().anyRequest().authenticated();
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/rest/api/user").access("hasAnyRole('USER')")
//                .antMatchers("/rest/api/admin").hasRole("ADMIN")
//                .antMatchers("/rest/api/**").authenticated();
    }

    private static class OAuthRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            // Determine if the resource called is "/api/**"
            String api = "/rest/api/";
            int length = api.length();
            String path = request.getServletPath();
            if (path.length() >= length) {
                path = path.substring(0, length);
                boolean isApi = path.equals(api);
                return isApi;
            }
            return false;
        }
    }
}