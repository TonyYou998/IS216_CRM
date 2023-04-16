package com.uit.crm.common.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try {
                String token=SpringBeanUtil.getBean(JwtUtils.class).getJwtTokenFromRequest(request);
                if(token!=null && SpringBeanUtil.getBean(JwtUtils.class).validadteJwtToken(token)){
                    String username=SpringBeanUtil.getBean(JwtUtils.class).getUsernameFromToken(token);
                    String email=SpringBeanUtil.getBean(JwtUtils.class).getEmailFromToken(token);
//                    UserDetails userDetails=SpringBeanUtil.getBean(UserDetailsService.class).loadUserByUsername(username);
                    UserDetails userDetails=SpringBeanUtil.getBean(UserDetailsService.class).loadUserByUsername(email);
                    Authentication auth=new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            filterChain.doFilter(request,response);
    }
}
