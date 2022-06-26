package com.gianca1994.aowebbackend.configurations;

import com.gianca1994.aowebbackend.model.Class;
import com.gianca1994.aowebbackend.repository.ClassRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassConfiguration {
    @Bean
    public CommandLineRunner autoSaveClasses(ClassRepository classRepository) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to auto save the classes in the database.
         * @param ClassRepository classRepository
         * @return CommandLineRunner
         */
        return args -> {
            classRepository.save(new Class(1L, "mage", 1, 1, 3, 2, 2));
            classRepository.save(new Class(2L, "warrior", 3, 1, 1, 3, 1));
            classRepository.save(new Class(3L, "archer", 1, 3, 1, 2, 2));
        };
    }
}
