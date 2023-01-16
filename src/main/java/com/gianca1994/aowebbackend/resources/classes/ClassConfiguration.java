package com.gianca1994.aowebbackend.resources.classes;

import com.gianca1994.aowebbackend.config.ModifConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: ClassConfiguration
 */
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
            List<Class> classes = classRepository.findAll();
            if (classes.isEmpty()) {
                classRepository.save(new Class(1L, ModifConfig.MAGE_NAME, ModifConfig.MAGE_START_STR, ModifConfig.MAGE_START_DEX, ModifConfig.MAGE_START_INT, ModifConfig.MAGE_START_VIT, ModifConfig.MAGE_START_LUK));
                classRepository.save(new Class(2L, ModifConfig.WARRIOR_NAME, ModifConfig.WARRIOR_START_STR, ModifConfig.WARRIOR_START_DEX, ModifConfig.WARRIOR_START_INT, ModifConfig.WARRIOR_START_VIT, ModifConfig.WARRIOR_START_LUK));
                classRepository.save(new Class(3L, ModifConfig.ARCHER_NAME, ModifConfig.ARCHER_START_STR, ModifConfig.ARCHER_START_DEX, ModifConfig.ARCHER_START_INT, ModifConfig.ARCHER_START_VIT, ModifConfig.ARCHER_START_LUK));
            }
        };
    }
}
