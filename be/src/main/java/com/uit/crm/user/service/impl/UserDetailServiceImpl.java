package com.uit.crm.user.service.impl;

import com.uit.crm.user.dto.UserDetailsDto;
import com.uit.crm.user.model.User;
import com.uit.crm.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthorizeCallback;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repository.findByEmail(username);
        Set<GrantedAuthority> authorities=new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ user.getRole().getRoleName()));

//        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
        return new UserDetailsDto(user.getUsername(),user.getPassword(),user.getEmail(),authorities);
    }


}
