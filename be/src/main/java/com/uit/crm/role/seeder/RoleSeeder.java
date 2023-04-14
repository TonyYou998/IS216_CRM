package com.uit.crm.role.seeder;

import com.uit.crm.role.model.Role;
import com.uit.crm.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoleSeeder implements CommandLineRunner {
    private  RoleRepository repository;


    @Override
    public void run(String... args) throws Exception {
        Role role=new Role("Employee");
        repository.save(role);
    }
}
