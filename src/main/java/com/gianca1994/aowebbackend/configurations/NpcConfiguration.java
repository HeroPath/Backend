package com.gianca1994.aowebbackend.configurations;

import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.repository.NpcRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NpcConfiguration {
    @Bean
    public CommandLineRunner autoSaveNpcs(NpcRepository npcRepository) {
        return args -> {
            npcRepository.save(new Npc(1L, "Murcielago", 10L, 20L, 100L, 200L, 100, 100, 1, 3, 100));
            npcRepository.save(new Npc(2L, "Lobo", 30L, 60L, 100L, 400L, 100, 100, 5, 7, 100));
        };
    }
}
