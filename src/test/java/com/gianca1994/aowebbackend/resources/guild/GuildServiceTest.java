package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.classes.ClassRepository;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.equipment.EquipmentRepository;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.inventory.InventoryRepository;
import com.gianca1994.aowebbackend.resources.jwt.JWTAuthController;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.role.RoleRepository;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GuildServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private GuildService guildService;

    @Autowired
    private JWTAuthController jwtAuthController;

    private User userTest;

    @BeforeEach
    void setUp() throws Conflict {
        guildRepository.deleteAll();

        if (userRepository.findAll().size() >= 1) {
            userTest = userRepository.findAll().get(0);
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("testusername");
            userDTO.setPassword("test");
            userDTO.setEmail("test@test.com");
            userDTO.setClassId(1);

            jwtAuthController.saveUser(userDTO);
            userTest = userRepository.findByUsername("testusername");
        }

        Guild guild = new Guild();
        guild.setName("testguild");
        guild.setDescription("testdescription");
        guild.setTag("testtag");
        guild.setLeader(userTest.getUsername());
        guild.setSubLeader("");
        guild.getMembers().add(userTest);
        guildRepository.save(guild);
    }

    @Test
    void givenGuild_whenGetAllGuilds_thenReturnGuildList() {
        assertThat(guildService.getAllGuilds().size()).isEqualTo(1);
    }
}