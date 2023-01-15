package com.gianca1994.aowebbackend.resources.title;


import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
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
                titleRepository.save(new Title("iron", 1, 0, 0, 0, 0, 0, 0));
                titleRepository.save(new Title("bronze", 10, 50, 5, 5, 5, 5, 5));
                titleRepository.save(new Title("silver", 20, 100, 10, 10, 10, 10, 10));
                titleRepository.save(new Title("gold", 40, 200, 20, 20, 20, 20, 20));
                titleRepository.save(new Title("platinum", 80, 400, 40, 40, 40, 40, 40));
                titleRepository.save(new Title("diamond", 150, 1000, 100, 100, 100, 100, 100));
                titleRepository.save(new Title("challenger", 200, 2500, 200, 200, 200, 200, 200));
            }
        };
    }
}
