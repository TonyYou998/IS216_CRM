package com.uit.crm.role.seeder;

import com.uit.crm.role.model.Role;
import com.uit.crm.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoleSeeder implements CommandLineRunner {
    private  RoleRepository repository;


    @Override
    public void run(String... args) throws Exception {
        Role r1=new Role("Employee");
        Role r2=new Role("Admin");
        Role r3=new Role("Leader");
        repository.save(r1);
        repository.save(r2);
        repository.save(r3);
    }
}
