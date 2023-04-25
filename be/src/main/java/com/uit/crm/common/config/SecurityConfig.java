package com.uit.crm.common.config;

import com.uit.crm.common.Constants;
import com.uit.crm.common.utils.JwtAuthorizationFilter;
import com.uit.crm.common.utils.SpringBeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ObservationAuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors();

                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.csrf().disable();
                http.addFilterBefore(SpringBeanUtil.getBean(JwtAuthorizationFilter.class), UsernamePasswordAuthenticationFilter.class);
            http.authorizeHttpRequests(authorize->authorize
                    .requestMatchers("/api/v1/admin/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/api/v1/user/task/leader/**")
                    .hasRole("LEADER")
                    .anyRequest()
                    .permitAll()
            );
            return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(Constants.BCRYPT);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


}
