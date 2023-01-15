package com.gianca1994.aowebbackend.resources.role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: RoleConfiguration
 */
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
