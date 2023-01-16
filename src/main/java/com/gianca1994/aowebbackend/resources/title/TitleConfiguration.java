package com.gianca1994.aowebbackend.resources.title;

import com.gianca1994.aowebbackend.config.ModifConfig;
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
                titleRepository.save(new Title(ModifConfig.TITLE1_NAME, ModifConfig.TITLE1_MIN_LVL, ModifConfig.TITLE1_MIN_PTS, ModifConfig.TITLE1_STR, ModifConfig.TITLE1_DEX, ModifConfig.TITLE1_INT, ModifConfig.TITLE1_VIT, ModifConfig.TITLE1_LUK));
                titleRepository.save(new Title(ModifConfig.TITLE2_NAME, ModifConfig.TITLE2_MIN_LVL, ModifConfig.TITLE2_MIN_PTS, ModifConfig.TITLE2_STR, ModifConfig.TITLE2_DEX, ModifConfig.TITLE2_INT, ModifConfig.TITLE2_VIT, ModifConfig.TITLE2_LUK));
                titleRepository.save(new Title(ModifConfig.TITLE3_NAME, ModifConfig.TITLE3_MIN_LVL, ModifConfig.TITLE3_MIN_PTS, ModifConfig.TITLE3_STR, ModifConfig.TITLE3_DEX, ModifConfig.TITLE3_INT, ModifConfig.TITLE3_VIT, ModifConfig.TITLE3_LUK));
                titleRepository.save(new Title(ModifConfig.TITLE4_NAME, ModifConfig.TITLE4_MIN_LVL, ModifConfig.TITLE4_MIN_PTS, ModifConfig.TITLE4_STR, ModifConfig.TITLE4_DEX, ModifConfig.TITLE4_INT, ModifConfig.TITLE4_VIT, ModifConfig.TITLE4_LUK));
                titleRepository.save(new Title(ModifConfig.TITLE5_NAME, ModifConfig.TITLE5_MIN_LVL, ModifConfig.TITLE5_MIN_PTS, ModifConfig.TITLE5_STR, ModifConfig.TITLE5_DEX, ModifConfig.TITLE5_INT, ModifConfig.TITLE5_VIT, ModifConfig.TITLE5_LUK));
                titleRepository.save(new Title(ModifConfig.TITLE6_NAME, ModifConfig.TITLE6_MIN_LVL, ModifConfig.TITLE6_MIN_PTS, ModifConfig.TITLE6_STR, ModifConfig.TITLE6_DEX, ModifConfig.TITLE6_INT, ModifConfig.TITLE6_VIT, ModifConfig.TITLE6_LUK));
                titleRepository.save(new Title(ModifConfig.TITLE7_NAME, ModifConfig.TITLE7_MIN_LVL, ModifConfig.TITLE7_MIN_PTS, ModifConfig.TITLE7_STR, ModifConfig.TITLE7_DEX, ModifConfig.TITLE7_INT, ModifConfig.TITLE7_VIT, ModifConfig.TITLE7_LUK));
            }
        };
    }
}
