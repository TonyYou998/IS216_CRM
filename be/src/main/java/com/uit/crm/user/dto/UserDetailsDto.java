package com.uit.crm.user.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Getter
public class UserDetailsDto extends User implements UserDetails {
        private String email;
    public UserDetailsDto(String username, String password, String email,Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email=email;

    }

}
