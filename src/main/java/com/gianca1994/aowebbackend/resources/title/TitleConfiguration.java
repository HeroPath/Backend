package com.gianca1994.aowebbackend.resources.title;

import com.gianca1994.aowebbackend.config.ModifierConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Modifier;
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
                titleRepository.save(new Title(ModifierConfig.TITLE1_NAME, ModifierConfig.TITLE1_MIN_LVL, ModifierConfig.TITLE1_MIN_PTS, ModifierConfig.TITLE1_STR, ModifierConfig.TITLE1_DEX, ModifierConfig.TITLE1_INT, ModifierConfig.TITLE1_VIT, ModifierConfig.TITLE1_LUK));
                titleRepository.save(new Title(ModifierConfig.TITLE2_NAME, ModifierConfig.TITLE2_MIN_LVL, ModifierConfig.TITLE2_MIN_PTS, ModifierConfig.TITLE2_STR, ModifierConfig.TITLE2_DEX, ModifierConfig.TITLE2_INT, ModifierConfig.TITLE2_VIT, ModifierConfig.TITLE2_LUK));
                titleRepository.save(new Title(ModifierConfig.TITLE3_NAME, ModifierConfig.TITLE3_MIN_LVL, ModifierConfig.TITLE3_MIN_PTS, ModifierConfig.TITLE3_STR, ModifierConfig.TITLE3_DEX, ModifierConfig.TITLE3_INT, ModifierConfig.TITLE3_VIT, ModifierConfig.TITLE3_LUK));
                titleRepository.save(new Title(ModifierConfig.TITLE4_NAME, ModifierConfig.TITLE4_MIN_LVL, ModifierConfig.TITLE4_MIN_PTS, ModifierConfig.TITLE4_STR, ModifierConfig.TITLE4_DEX, ModifierConfig.TITLE4_INT, ModifierConfig.TITLE4_VIT, ModifierConfig.TITLE4_LUK));
                titleRepository.save(new Title(ModifierConfig.TITLE5_NAME, ModifierConfig.TITLE5_MIN_LVL, ModifierConfig.TITLE5_MIN_PTS, ModifierConfig.TITLE5_STR, ModifierConfig.TITLE5_DEX, ModifierConfig.TITLE5_INT, ModifierConfig.TITLE5_VIT, ModifierConfig.TITLE5_LUK));
                titleRepository.save(new Title(ModifierConfig.TITLE6_NAME, ModifierConfig.TITLE6_MIN_LVL, ModifierConfig.TITLE6_MIN_PTS, ModifierConfig.TITLE6_STR, ModifierConfig.TITLE6_DEX, ModifierConfig.TITLE6_INT, ModifierConfig.TITLE6_VIT, ModifierConfig.TITLE6_LUK));
                titleRepository.save(new Title(ModifierConfig.TITLE7_NAME, ModifierConfig.TITLE7_MIN_LVL, ModifierConfig.TITLE7_MIN_PTS, ModifierConfig.TITLE7_STR, ModifierConfig.TITLE7_DEX, ModifierConfig.TITLE7_INT, ModifierConfig.TITLE7_VIT, ModifierConfig.TITLE7_LUK));
            }
        };
    }
}
