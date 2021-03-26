package com.interview.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.interview.configuration.ServerCredentials;

@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    public static class WebServiceSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private ServerCredentials serverCredentials;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .antMatcher("/api/**").authorizeRequests().anyRequest()
                    .hasAnyRole("WEBSERVICE_ROLE")
                    .and()
                    .httpBasic();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            BCryptPasswordEncoder passwordEncoder = passwordEncoder();
            auth.inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .withUser(serverCredentials.getWebserviceName())
                    .password(passwordEncoder.encode(serverCredentials.getWebservicePassword()))
                    .roles("WEBSERVICE_ROLE");
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}
