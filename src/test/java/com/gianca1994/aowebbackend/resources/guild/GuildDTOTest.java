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
    void getNameTest() {
        assertEquals("Test", guildDTO.getName());
    }

    @Test
    void setNameTest() {
        guildDTO.setName("Test2");
        assertEquals("Test2", guildDTO.getName());
    }

    @Test
    void getDescriptionTest() {
        assertEquals("Test", guildDTO.getDescription());
    }

    @Test
    void setDescriptionTest() {
        guildDTO.setDescription("Test2");
        assertEquals("Test2", guildDTO.getDescription());
    }

    @Test
    void getTagTest() {
        assertEquals("Test", guildDTO.getTag());
    }

    @Test
    void setTagTest() {
        guildDTO.setTag("Test2");
        assertEquals("Test2", guildDTO.getTag());
    }
}