package com.gianca1994.aowebbackend.configurations;

import com.gianca1994.aowebbackend.model.Class;
import com.gianca1994.aowebbackend.model.Role;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleConfiguration {
    @Bean
    public CommandLineRunner autoSaveRoles(RoleRepository roleRepository) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to auto save the roles in the database.
         * @param RoleRepository roleRepository
         * @return CommandLineRunner
         */
        return args -> {
            List<Role> roles = roleRepository.findAll();
            if (roles.isEmpty()) {
                roleRepository.save(new Role(1L, "STANDARD"));
                roleRepository.save(new Role(2L, "ADMIN"));
            }
        };
    }
}
