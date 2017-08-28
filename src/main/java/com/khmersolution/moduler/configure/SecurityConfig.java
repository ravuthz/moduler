package com.khmersolution.moduler.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Vannaravuth Yo
 * Date : 8/28/2017, 2:48 PM
 * Email : ravuthz@gmail.com
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SuppressWarnings({"SpringJavaAutowiringInspection"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsService userDetailsService;

//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().fullyAuthenticated()
//                .antMatchers("/user/**").hasAuthority("USER")
//                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .and().httpBasic()
//                .and().csrf().disable();
//    }

    /**
     * Constructor disables the default security settings
     */
    public SecurityConfig() {
        super(true);
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/login");
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

}
