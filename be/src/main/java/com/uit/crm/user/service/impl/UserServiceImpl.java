package com.uit.crm.user.service.impl;

import com.uit.crm.common.utils.JwtUtils;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.role.model.Role;
import com.uit.crm.role.repository.RoleRepository;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.model.User;
import com.uit.crm.user.repository.UserRepository;
import com.uit.crm.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);
//    private UserRepository userRepository;
    private ModelMapper mapper;
//    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    @Override
    public UserDto createUser(UserDto dto) {
        Role role= SpringBeanUtil.getBean(RoleRepository.class).findByRoleName("Employee");

        User user=new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
//        user.setPassword(encoder.encode(dto.getPassword()));
        user.setPassword(SpringBeanUtil.getBean(PasswordEncoder.class).encode(dto.getPassword()));
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user.setFullName(dto.getFullName());
        user.setRole(role);
//        userRepository.save(user);
        SpringBeanUtil.getBean(UserRepository.class).save(user);
        UserDto response= mapper.map(user,UserDto.class);
        return response;

    }

    public UserDto login(UserDto request) {
        UserDto response = new UserDto();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtUtils.generateJwtToken(auth);

            response.setToken(token);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return response;
    }
}
