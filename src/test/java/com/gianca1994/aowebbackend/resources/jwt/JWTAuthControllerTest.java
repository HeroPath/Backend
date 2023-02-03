package com.gianca1994.aowebbackend.resources.jwt;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.request.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JWTAuthControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTAuthController jwtAuthController;

    @BeforeEach
    void setUp() throws Conflict {
        if (userRepository.findAll().size() == 0) {
            UserRegisterDTO userDTO = new UserRegisterDTO();
            userDTO.setUsername("testusername");
            userDTO.setPassword("test");
            userDTO.setEmail("test@test.com");
            userDTO.setClassName("test");

            jwtAuthController.saveUser(userDTO);
        }
    }
}