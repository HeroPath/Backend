package com.gianca1994.aowebbackend.resources.guild;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GuildServiceTest {

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private GuildService guildService;

    private Guild guild;

    @BeforeEach
    void setUp() {
        guildRepository.deleteAll();
        guild = new Guild();
        guild.setName("test");
        guild.setDescription("test");
        guild.setTag("test");
        guild.setLeader("test");
        guild.setSubLeader("test");
        guild.setMembers(new HashSet<>());
        guildRepository.save(guild);
    }

    @Test
    void getAllGuilds() {
        assertThat(guildService.getAllGuilds().size()).isEqualTo(1);
    }

    @Test
    void getGuildByName() {
        assertThat(guildService.getGuildByName("test")).isNotNull();
    }

    @Test
    void guildToObjectNodeTest() {
        assertThat(guildService.guildToObjectNode(guild)).isNotNull();
    }


}