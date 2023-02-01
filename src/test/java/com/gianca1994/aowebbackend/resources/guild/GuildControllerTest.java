package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.jwt.JWTAuthController;
import com.gianca1994.aowebbackend.resources.jwt.JwtRequestDTO;
import com.gianca1994.aowebbackend.resources.jwt.JwtResponseDTO;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.request.UserRegisterDTO;
import io.swagger.v3.core.util.Json;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GuildControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTAuthController jwtAuthController;

    private String bearerToken;

    @BeforeEach
    void setUp() throws Exception {
        if (userRepository.findAll().size() == 0) {
            UserRegisterDTO userDTO = new UserRegisterDTO();
            userDTO.setUsername("testusername");
            userDTO.setPassword("test");
            userDTO.setEmail("test@test.com");
            userDTO.setClassId(1);

            jwtAuthController.saveUser(userDTO);
        }

        JwtRequestDTO authenticationRequest = new JwtRequestDTO();
        authenticationRequest.setUsername("testusername");
        authenticationRequest.setPassword("test");

        ResponseEntity<?> loginRequest = jwtAuthController.createAuthenticationToken(
                authenticationRequest
        );

        JwtResponseDTO tokenObject = (JwtResponseDTO) Objects.requireNonNull(loginRequest.getBody());
        bearerToken = tokenObject.getToken();
    }

    @Test
    @Order(1)
    void testGetAllGuilds_success() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/guilds")
                                .header("Authorization", "Bearer " + bearerToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @Order(2)
    void testGetUserGuild_success_FALSE() throws Exception {
        JSONObject expectedJsonObj = new JSONObject();
        expectedJsonObj.put("userInGuild", false);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/guilds/in-guild")
                                .header("Authorization", "Bearer " + bearerToken)
                )
                .andExpect(content().json(expectedJsonObj.toString()));
    }

    @Test
    @Order(5)
    void testGetUserGuild_success_TRUE() throws Exception {
        JSONObject expectedJsonObj = new JSONObject();
        expectedJsonObj.put("userInGuild", true);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/guilds/in-guild")
                                .header("Authorization", "Bearer " + bearerToken)
                )
                .andExpect(content().json(expectedJsonObj.toString()));
    }

    @Test
    @Order(3)
    void testCreateGuild_Conflict() throws Exception {
        GuildDTO guildDTO = new GuildDTO();
        guildDTO.setName("testGuild");
        guildDTO.setDescription("testDescription");
        guildDTO.setTag("testTag");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/guilds")
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(guildDTO))
        ).andExpect(status().isConflict());
    }

    @Test
    @Order(4)
    void testCreateGuild_Success() throws Exception {
        GuildDTO guildDTO = new GuildDTO();
        guildDTO.setName("testGuild");
        guildDTO.setDescription("testDescription");
        guildDTO.setTag("testTag");

        JSONObject expectedJsonObj = new JSONObject();
        expectedJsonObj.put("userInGuild", false);

        User user = userRepository.findByUsername("testusername");
        user.setGold(SvConfig.GOLD_TO_CREATE_GUILD);
        user.setLevel((short) SvConfig.LEVEL_TO_CREATE_GUILD);
        user.setDiamond(SvConfig.DIAMOND_TO_CREATE_GUILD);
        userRepository.save(user);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/guilds")
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(guildDTO))
        ).andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void testRequestGuild_Success() throws Exception {

        if (userRepository.findAll().size() == 1) {
            UserRegisterDTO userDTO = new UserRegisterDTO();
            userDTO.setUsername("testusername2");
            userDTO.setPassword("test");
            userDTO.setEmail("test2@test.com");
            userDTO.setClassId(1);

            jwtAuthController.saveUser(userDTO);
        }

        User user = userRepository.findByUsername("testusername2");
        user.setLevel((short) SvConfig.LEVEL_TO_JOIN_GUILD);
        userRepository.save(user);

        JwtRequestDTO authenticationRequest = new JwtRequestDTO();
        authenticationRequest.setUsername("testusername2");
        authenticationRequest.setPassword("test");

        ResponseEntity<?> loginRequest = jwtAuthController.createAuthenticationToken(
                authenticationRequest
        );

        JwtResponseDTO tokenObject = (JwtResponseDTO) Objects.requireNonNull(loginRequest.getBody());
        String bearerTokenUser2 = tokenObject.getToken();

        RequestGuildNameDTO requestGuildNameDTO = new RequestGuildNameDTO();
        requestGuildNameDTO.setName("testGuild");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/guilds/request")
                        .header("Authorization", "Bearer " + bearerTokenUser2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(requestGuildNameDTO))
        ).andExpect(status().isOk());
    }

}
