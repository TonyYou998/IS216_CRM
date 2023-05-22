package com.uit.crm.user.seeder;

import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.role.model.Role;
import com.uit.crm.role.repository.RoleRepository;
import com.uit.crm.user.model.User;
import com.uit.crm.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class UserSeeder implements CommandLineRunner {
    @Transactional
    @Override
    public void run(String... args) throws Exception {

        if(Arrays.asList(args).contains("--seed-data")){
            Role r=SpringBeanUtil.getBean(RoleRepository.class).findById(Long.parseLong("2")).orElse(null);
            Role r2=SpringBeanUtil.getBean(RoleRepository.class).findById(Long.parseLong("3")).orElse(null);
            User admin=new User("admin",SpringBeanUtil.getBean(PasswordEncoder.class).encode("1234"),r,"0123xxx","asdasd","dasdasda","admin@gmail.com");
            User employee=new User("tanvuu",SpringBeanUtil.getBean(PasswordEncoder.class).encode("1234"),r2,"0123213xxx","asdasd","kalial","tanvuu998@gmail.com");
            SpringBeanUtil.getBean(UserRepository.class).save(admin);
            SpringBeanUtil.getBean(UserRepository.class).save(employee);
        }


    }
}
