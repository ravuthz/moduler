package com.khmersolution.moduler.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resourceId}")
    private String resourceId;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(resourceId)
                .tokenServices(tokenServices)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(new OAuthRequestedMatcher())
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
//        http
//                .requestMatcher(new OAuthRequestedMatcher())
//                .cors().and().anonymous()
//
//                .and().csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .authorizeRequests()
//
//                // Allow anonymous resource requests
//                .antMatchers(
//                        "/",
//                        "/v2/api-docs",
//                        "/swagger-resources",
//                        "/configuration/ui",
//                        "/configuration/security",
//                        "/swagger-ui.html",
//                        "/webjars/**",
//                        "/rest/api/**",
//                        "/oauth/**"
//                ).permitAll()
//
//                .anyRequest().authenticated()
//
//                .and().exceptionHandling().authenticationEntryPoint(
//                entryPoint
////                (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
//        )
//
//                .and().headers().cacheControl();
//        ;
    }

    private static class OAuthRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            System.out.println("auth: " + auth);
            System.out.println("request: " + request.getServletPath());
            // Determine if the client request contained an OAuth Authorization
            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
            boolean haveAccessToken = request.getParameter("access_token") != null;
            return haveOauth2Token || haveAccessToken;
        }
    }

}