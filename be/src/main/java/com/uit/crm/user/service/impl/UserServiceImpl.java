package com.uit.crm.user.service.impl;

import com.uit.crm.common.utils.JwtUtils;
import com.uit.crm.common.utils.LoggerUtil;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.role.model.Role;
import com.uit.crm.role.repository.RoleRepository;
import com.uit.crm.user.dto.GetUserDto;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.model.User;
import com.uit.crm.user.repository.UserRepository;
import com.uit.crm.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
//    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);
//    private UserRepository userRepository;
    private ModelMapper mapper;
//    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    @Override
    public UserDto createUser(GetUserDto dto) {
        Role role= SpringBeanUtil.getBean(RoleRepository.class).findByRoleName("Employee");
        User user=new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setPassword(SpringBeanUtil.getBean(PasswordEncoder.class).encode(dto.getPassword()));
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user.setFullName(dto.getFullName());
        user.setRole(role);
        SpringBeanUtil.getBean(UserRepository.class).save(user);
        UserDto response= mapper.map(user,UserDto.class);
        response.setRoleId(user.getRole().getId().toString());
        return response;

    }

    @Override
    public UserDto createAdminAccount(GetUserDto request) {
        Role role = SpringBeanUtil.getBean(RoleRepository.class).findByRoleName("Admin");
        User u = new User();
        UserDto response = null;
        if (!role.getRoleName().isEmpty()) {
            u.setFullName(request.getFullName());
            u.setEmail(request.getEmail());
            u.setAddress(request.getAddress());
            u.setPhone(request.getPhone());
            u.setUsername(request.getUsername());
            u.setPassword(SpringBeanUtil.getBean(PasswordEncoder.class).encode(request.getPassword()));
            u.setRole(role);
            SpringBeanUtil.getBean(UserRepository.class).save(u);
            response = mapper.map(u, UserDto.class);
            response.setRoleId(u.getRole().getId().toString());

            return response;
        } else {
            return response=mapper.map(u,UserDto.class);
        }


    }

    @Override
    public List<UserDto> getAllAccounts() {
        List<UserDto> responses=new ArrayList<>();
        List<User> lstUser=SpringBeanUtil.getBean(UserRepository.class).findAll();
        for (User user : lstUser) {
            UserDto dto= mapper.map(user, UserDto.class);

            dto.setRoleId(user.getRole().getId().toString());
            responses.add(dto);
        }
        return  responses;

    }

    @Override
    public List<UserDto> getAllLeaders() {
        try{
            Role r=SpringBeanUtil.getBean(RoleRepository.class).findById(Long.parseLong("3")).orElse(null);

            List<User> lstUser=SpringBeanUtil.getBean(UserRepository.class).findAllByRole(r);
            List<UserDto> lstDto=new LinkedList<>();
            for(User u:lstUser){
                UserDto dto=mapper.map(u,UserDto.class);
                lstDto.add(dto);
            }
            return lstDto;
        }
        catch (Exception ex){
            SpringBeanUtil.getBean(LoggerUtil.class).logger(UserServiceImpl.class).info(ex.getMessage());
            return null;
        }

    }

    public UserDto login(GetUserDto request) {
        UserDto response = new UserDto();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
//            User u=SpringBeanUtil.getBean(UserRepository.class).findByEmail(request.getEmail());
            String token = jwtUtils.generateJwtToken(auth);

            response.setToken(token);

        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
            SpringBeanUtil.getBean(LoggerUtil.class).logger(UserServiceImpl.class).info(e.getMessage());
        }
        return response;
    }
}
