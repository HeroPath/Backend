package com.gianca1994.aowebbackend.resources.guild;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuildDTOTest {

    private GuildDTO guildDTO;

    @BeforeEach
    void setUp() {
        guildDTO = new GuildDTO();
        guildDTO.setName("Test");
        guildDTO.setDescription("Test");
        guildDTO.setTag("Test");
    }

    @Test
    void constructorNotArgsTest(){
        GuildDTO guildDTO = new GuildDTO();
        assertNull(guildDTO.getName());
        assertNull(guildDTO.getDescription());
        assertNull(guildDTO.getTag());
    }

    @Test
    void constructorArgsTest(){
        GuildDTO guildDTO = new GuildDTO("Test", "Test", "Test");
        assertEquals("Test", guildDTO.getName());
        assertEquals("Test", guildDTO.getDescription());
        assertEquals("Test", guildDTO.getTag());
    }

    @Test
    void givenUserDTO_whenGetNewUserDTO_thenReturnNameUserDTO() {
        assertEquals("Test", guildDTO.getName());
    }

    @Test
    void givenUserDTO_whenGetNewUserDTO_thenReturnDescriptionUserDTO() {
        assertEquals("Test", guildDTO.getDescription());
    }

    @Test
    void givenUserDTO_whenGetNewUserDTO_thenReturnTagUserDTO() {
        assertEquals("Test", guildDTO.getTag());
    }
}