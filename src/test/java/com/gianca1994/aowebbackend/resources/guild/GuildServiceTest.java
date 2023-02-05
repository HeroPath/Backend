package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.jwt.AuthController;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.request.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
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
    private AuthController authController;

    private User userTest;

    @BeforeEach
    void setUp() throws Conflict {
        guildRepository.deleteAll();

        if (userRepository.findAll().size() >= 1) {
            userTest = userRepository.findAll().get(0);
        } else {
            UserRegisterDTO userDTO = new UserRegisterDTO();
            userDTO.setUsername("testusername");
            userDTO.setPassword("test");
            userDTO.setEmail("test@test.com");
            userDTO.setClassName("test");

            authController.register(userDTO);
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
}