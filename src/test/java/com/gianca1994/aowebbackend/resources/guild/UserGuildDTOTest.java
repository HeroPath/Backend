package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.resources.user.dto.response.UserGuildDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGuildDTOTest {

    UserGuildDTO userGuildDTO;

    @BeforeEach
    void setUp() {
        userGuildDTO = new UserGuildDTO();
        userGuildDTO.setUsername("test");
        userGuildDTO.setLevel((short) 1);
        userGuildDTO.setTitlePoints(1);
        userGuildDTO.setAClass("test");
        userGuildDTO.setTitleName("test");
    }

    @Test
    void constructorAllArgs() {
        UserGuildDTO userGuildDTOTest = new UserGuildDTO("test", (short) 1, 1, "test", "test");
        assertEquals("test", userGuildDTOTest.getUsername());
        assertEquals(1, userGuildDTOTest.getLevel());
        assertEquals(1, userGuildDTOTest.getTitlePoints());
        assertEquals("test", userGuildDTOTest.getAClass());
        assertEquals("test", userGuildDTOTest.getTitleName());
    }

    @Test
    void constructorNoArgs() {
        UserGuildDTO userGuildDTOTest = new UserGuildDTO();
        assertNull(userGuildDTOTest.getUsername());
    }

    @Test
    void givenUserGuildDTO_whenGetUsername_thenReturnUsername() {
        assertEquals("test", userGuildDTO.getUsername());
    }

    @Test
    void givenUserGuildDTO_whenGetLevel_thenReturnLevel() {
        assertEquals(1, userGuildDTO.getLevel());
    }

    @Test
    void givenUserGuildDTO_whenGetTitlePoints_thenReturnTitlePoints() {
        assertEquals(1, userGuildDTO.getTitlePoints());
    }

    @Test
    void givenUserGuildDTO_whenGetClassName_thenReturnClassName() {
        assertEquals("test", userGuildDTO.getAClass());
    }

    @Test
    void givenUserGuildDTO_whenGetTitleName_thenReturnTitleName() {
        assertEquals("test", userGuildDTO.getTitleName());
    }

}