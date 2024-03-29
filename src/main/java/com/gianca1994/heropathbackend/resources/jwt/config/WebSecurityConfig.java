package com.gianca1994.heropathbackend.resources.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to configure the web security.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Value("${jwt.authorizeRequests.patterns.auth}")
    private String AUTHORIZE_PATTERNS_AUTHORIZATION;

    @Value("${jwt.authorizeRequests.patterns.classes}")
    private String AUTHORIZE_PATTERNS_CLASSES;

    @Value("${jwt.authorizeRequests.patterns.swagger.docs}")
    private String AUTHORIZE_PATTERNS_SWAGGER_DOCS;

    @Value("${jwt.authorizeRequests.patterns.server.stats}")
    private String AUTHORIZE_PATTERNS_SERVER_STATS;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to configure the authentication manager.
         * @param AuthenticationManagerBuilder auth
         * @return void
         */
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to encode the password.
         * @param void
         * @return PasswordEncoder
         */
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to configure the authentication manager.
         * @param void
         * @return AuthenticationManager
         */
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to configure the http security.
         * @param HttpSecurity http
         * @return void
         */
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        http.authorizeRequests()
                .antMatchers(AUTHORIZE_PATTERNS_AUTHORIZATION).permitAll()
                .antMatchers(AUTHORIZE_PATTERNS_CLASSES).permitAll()
                .antMatchers(AUTHORIZE_PATTERNS_SWAGGER_DOCS).permitAll()
                .antMatchers(AUTHORIZE_PATTERNS_SERVER_STATS).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
