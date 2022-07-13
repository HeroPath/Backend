package com.gianca1994.aowebbackend.configurations;


import com.gianca1994.aowebbackend.model.Role;
import com.gianca1994.aowebbackend.model.Title;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import com.gianca1994.aowebbackend.repository.TitleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: TitleConfiguration
 */

@Configuration
public class TitleConfiguration {
    @Bean
    public CommandLineRunner autoSaveTitles(TitleRepository titleRepository) {
        /**
         * @Author: Gianca1994
         * Explanation:
         * @param RoleRepository roleRepository
         * @return CommandLineRunner
         */
        return args -> {
            List<Title> roles = titleRepository.findAll();
            if (roles.isEmpty()) {
                titleRepository.save(new Title("iron", 1, 0));
                titleRepository.save(new Title("bronze", 10, 100));
                titleRepository.save(new Title("silver", 30, 200));
                titleRepository.save(new Title("gold", 100, 500));
                titleRepository.save(new Title("platinum", 150, 1000));
                titleRepository.save(new Title("diamond", 200, 2000));
                titleRepository.save(new Title("challenger", 270, 5000));
            }
        };
    }
}
