package com.gianca1994.aowebbackend.configurations;

import com.gianca1994.aowebbackend.model.Class;
import com.gianca1994.aowebbackend.model.Role;
import com.gianca1994.aowebbackend.repository.ClassRepository;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassConfiguration {
    @Bean
    public CommandLineRunner autoSaveClasses(ClassRepository classRepository) {
        return args -> {
            classRepository.save(new Class(1L, "Mage", 1, 2, 3, 1, 2));
            classRepository.save(new Class(2L, "Warrior", 3, 1, 1, 3, 1));
            classRepository.save(new Class(3L, "Archer", 2, 3, 1, 1, 2));
        };
    }
}
