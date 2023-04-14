package com.uit.crm.common.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtils {
    private Long jwtExpiration = 3600*30L;
    final String jwtSecret="tanvuu998";
    public String generateJwtToken(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        Date now=new Date();

        return  Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+jwtExpiration))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
}
