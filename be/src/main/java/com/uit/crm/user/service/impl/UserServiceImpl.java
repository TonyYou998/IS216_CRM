package com.uit.crm.user.service.impl;

import com.uit.crm.common.utils.JwtUtils;
import com.uit.crm.common.utils.LoggerUtil;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.project.model.Project;
import com.uit.crm.project.model.ProjectEmployee;
import com.uit.crm.project.repository.ProjectEmployeeRepository;
import com.uit.crm.project.repository.ProjectRepository;
import com.uit.crm.role.model.Role;
import com.uit.crm.role.repository.RoleRepository;
import com.uit.crm.task.model.Task;
import com.uit.crm.task.model.repository.TaskRepository;
import com.uit.crm.user.dto.GetUserDto;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.model.User;
import com.uit.crm.user.repository.UserRepository;
import com.uit.crm.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

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
        Role role= SpringBeanUtil.getBean(RoleRepository.class).findById(Long.parseLong(dto.getRoleId())).orElse(null);
//        Assert.isNull(role,"Role_ID not exist");
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

    @Override
    public List<UserDto> getAllEmployee(String authHeader) {
        try {
            String token=SpringBeanUtil.getBean(JwtUtils.class).getTokenFromHeader(authHeader);
            String email=SpringBeanUtil.getBean(JwtUtils.class).getEmailFromToken(token);
//            Assert.isNull(email,"User not exist");
            Role r=SpringBeanUtil.getBean(RoleRepository.class).findById(Long.parseLong("1")).orElse(null);
//            Assert.isNull(r,"This role not exist");
            List<User> lstEmployee=SpringBeanUtil.getBean(UserRepository.class).findAllByRole(r);
            List<UserDto> lstDto=new LinkedList<>();

            if(lstEmployee.size()>0){
                return getUserDtos(lstEmployee, lstDto);
            }
        }
        catch (Exception e){
            SpringBeanUtil.getBean(LoggerUtil.class).logger(UserServiceImpl.class).info(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public List<UserDto> getAllEmployeeInProject(String authHeader, String projectId) {
        try {
            String token=SpringBeanUtil.getBean(JwtUtils.class).getTokenFromHeader(authHeader);
            String email=SpringBeanUtil.getBean(JwtUtils.class).getEmailFromToken(token);
//            User u=SpringBeanUtil.getBean(UserRepository.class).findByEmail(email);
            Project p=SpringBeanUtil.getBean(ProjectRepository.class).findById(Long.parseLong(projectId)).orElse(null);

            List<ProjectEmployee>lstProjectEmployee= SpringBeanUtil.getBean(ProjectEmployeeRepository.class).findByProject(p);
            List<UserDto> lstDto=new LinkedList<>();
            for(ProjectEmployee pE: lstProjectEmployee){
                UserDto dto=mapper.map(pE.getUser(),UserDto.class);
                dto.setRoleId(pE.getUser().getRole().getId().toString());
                dto.setRoleName(pE.getUser().getRole().getRoleName());
                lstDto.add(dto);

            }
            return lstDto;



        }
        catch (Exception e){
            SpringBeanUtil.getBean(LoggerUtil.class).logger(UserService.class).info(e.getMessage());
        }
        return null;
    }

    @Override
    public List<UserDto> findByName(String username) {
        try{

            List<User> lstUser=SpringBeanUtil.getBean(UserRepository.class).findByUserName(username);
            List<UserDto> lstDto=new LinkedList<>();
            for(User u:lstUser){
                UserDto dto=mapper.map(u,UserDto.class);
                dto.setRoleName(u.getRole().getRoleName());
                dto.setRoleId(u.getRole().getId().toString());
                lstDto.add(dto);
            }
            return lstDto;
        }
        catch (Exception ex){

        }
        return null;
    }

    @Override
    public void sendEmailToUser(String email,String taskName) {
        try{
//            User u=SpringBeanUtil.getBean(UserRepository.class).findById(Long.parseLong(userId)).orElse(null);
            Assert.notNull(email,"email not exits");

            String senderEmail="tanvuu998@gmail.com";
            String senderPassword="mbthlabfyjaavrrx";
            Properties props=new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });
            try {
                // Create a new message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Overdue task");
                String notifycation=String.format("%s is overdue today",taskName);
                message.setText(notifycation);

                // Send the message
                Transport.send(message);

                System.out.println("Email sent successfully.");
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }
        catch (Exception ex){

        }
    }

    @Override
    public UserDto deleteUser(String userId) {
        try{
                User u=SpringBeanUtil.getBean(UserRepository.class).findById(Long.parseLong(userId)).orElse(null);
                List<Task> lstTask=SpringBeanUtil.getBean(TaskRepository.class).findAllByAssignedEmployeeId(u);
                List<ProjectEmployee> lstProjectEmployee=SpringBeanUtil.getBean(ProjectEmployeeRepository.class).findByUser(u);
                if(!lstTask.isEmpty())
                    SpringBeanUtil.getBean(TaskRepository.class).deleteAll(lstTask);
                if(u.getRole().getRoleName().equals("LEADER")){
                    List<Project> lstProject=SpringBeanUtil.getBean(ProjectRepository.class).findByProjectLeader(u);
                    for(Project p:lstProject){
                        p.setProjectLeader(null);
                        SpringBeanUtil.getBean(ProjectRepository.class).save(p);
                    }
                }

                if(!lstProjectEmployee.isEmpty())
                    SpringBeanUtil.getBean(ProjectEmployeeRepository.class).deleteAll(lstProjectEmployee);
                if(u!=null){
                    SpringBeanUtil.getBean(UserRepository.class).deleteById(Long.parseLong(userId));
                    return mapper.map(u,UserDto.class);
                }
        }
        catch (Exception ex){

        }
        return null;
    }

    private List<UserDto> getUserDtos(List<User> lstEmployee, List<UserDto> lstDto) {
        for(User u:lstEmployee){
            UserDto dto=mapper.map(u, UserDto.class);
            dto.setRoleId(u.getRole().getId().toString());
            dto.setRoleName(u.getRole().getRoleName());
            lstDto.add(dto);
        }
        return lstDto;
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
