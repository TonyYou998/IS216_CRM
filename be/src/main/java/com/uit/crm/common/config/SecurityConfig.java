package com.uit.crm.common.config;

import com.uit.crm.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ObservationAuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//        UserDetails admin= User.withUsername("root")
//                .password(encoder.encode("1234"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user=User.withUsername("user")
//                .password(encoder.encode("1234"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin,user);
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/admin/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/**")
                .permitAll()
                .and()
                .build();

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
