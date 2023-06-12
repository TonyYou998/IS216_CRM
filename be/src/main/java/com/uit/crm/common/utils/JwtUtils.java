package com.uit.crm.common.utils;

import com.uit.crm.user.dto.UserDetailsDto;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JwtUtils {
    private Long jwtExpiration = 108000000L;
    private String authHeader = "Authorization";
    private String tokenPrefix = "Bearer ";
    final String jwtSecret="tanvuu998";
    public String generateJwtToken(Authentication authentication){
//        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        Date now=new Date();
        UserDetailsDto userDetailsDto=(UserDetailsDto) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetailsDto.getAuthorities();
        String email=userDetailsDto.getEmail();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return  Jwts.builder()
                .claim("roles", roles)
                .claim("email",email)
                .setSubject(userDetailsDto.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+jwtExpiration))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public String getJwtTokenFromRequest(HttpServletRequest request) {
        String header=request.getHeader(authHeader);
        if(StringUtils.hasText(header)&&header.startsWith(tokenPrefix)){
            return header.substring(tokenPrefix.length());
        }
        return null;

    }
    public String getTokenFromHeader(String authHeader){
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(tokenPrefix)){
            return authHeader.substring(tokenPrefix.length());
        }
        return null;

    }

    public boolean validadteJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException e1) {
            System.out.println( e1.getMessage());
        } catch (ExpiredJwtException e2) {
            System.out.println( e2.getMessage());
        } catch (MalformedJwtException e3) {
            System.out.println( e3.getMessage());
        } catch (IllegalArgumentException e4) {
            System.out.println( e4.getMessage());
        } catch (UnsupportedJwtException e5) {
            System.out.println( e5.getMessage());
        }
        return false;

    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("email", String.class);
    }
}
