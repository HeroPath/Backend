package com.gianca1994.aowebbackend.resources.guild;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGuildDTOTest {

    UserGuildDTO userGuildDTO;

    @BeforeEach
    void setUp() {
        userGuildDTO = new UserGuildDTO("test", (short) 1, 1, "test", "test");
    }

    @Test
    void constructorAllArgs() {
        UserGuildDTO userGuildDTOTest = new UserGuildDTO("test", (short) 1, 1, "test", "test");
        assertEquals("test", userGuildDTOTest.getUsername());
        assertEquals(1, userGuildDTOTest.getLevel());
        assertEquals(1, userGuildDTOTest.getTitlePoints());
        assertEquals("test", userGuildDTOTest.getClassName());
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
        assertEquals("test", userGuildDTO.getClassName());
    }

    @Test
    void givenUserGuildDTO_whenGetTitleName_thenReturnTitleName() {
        assertEquals("test", userGuildDTO.getTitleName());
    }

    @Test
    void givenUserGuildDTO_whenSetUsername_thenReturnUsername() {
        userGuildDTO.setUsername("test2");
        assertEquals("test2", userGuildDTO.getUsername());
    }

    @Test
    void givenUserGuildDTO_whenSetLevel_thenReturnLevel() {
        userGuildDTO.setLevel((short) 2);
        assertEquals(2, userGuildDTO.getLevel());
    }

    @Test
    void givenUserGuildDTO_whenSetTitlePoints_thenReturnTitlePoints() {
        userGuildDTO.setTitlePoints(2);
        assertEquals(2, userGuildDTO.getTitlePoints());
    }

    @Test
    void givenUserGuildDTO_whenSetClassName_thenReturnClassName() {
        userGuildDTO.setClassName("test2");
        assertEquals("test2", userGuildDTO.getClassName());
    }

    @Test
    void givenUserGuildDTO_whenSetTitleName_thenReturnTitleName() {
        userGuildDTO.setTitleName("test2");
        assertEquals("test2", userGuildDTO.getTitleName());
    }
}