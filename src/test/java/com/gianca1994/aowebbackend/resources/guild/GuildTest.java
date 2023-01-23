package com.gianca1994.aowebbackend.resources.guild;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GuildTest {

    private Guild guildTest;

    @BeforeEach
    void setUp() {
        guildTest = new Guild();
        guildTest.setName("Test");
        guildTest.setDescription("Test");
        guildTest.setTag("Test");
        guildTest.setLeader("Test");
        guildTest.setSubLeader("Test");
        guildTest.setLevel((short) 1);
        guildTest.setDiamonds(1);
    }

    @Test
    void constructorNotArgsTest() {
        Guild guild = new Guild();
        assertNotNull(guild);
    }

    @Test
    void constructorArgsTest() {
        Guild guild = new Guild(1L,"Test", "Test", "Test", "Test", "Test", (short) 1, 1, new ArrayList<>());
        assertNotNull(guild);
    }

    @Test
    void getId() {
        Long idValue = 4L;
        guildTest.setId(idValue);
        assertEquals(idValue, guildTest.getId());
    }

    @Test
    void getName() {
        assertEquals("Test", guildTest.getName());
    }

    @Test
    void getDescription() {
        assertEquals("Test", guildTest.getDescription());
    }

    @Test
    void getTag() {
        assertEquals("Test", guildTest.getTag());
    }

    @Test
    void getLeader() {
        assertEquals("Test", guildTest.getLeader());
    }

    @Test
    void getSubLeader() {
        assertEquals("Test", guildTest.getSubLeader());
    }

    @Test
    void getLevel() {
        assertEquals(1, guildTest.getLevel());
    }

    @Test
    void getPoints() {
        assertEquals(1, guildTest.getDiamonds());
    }

    @Test
    void getMembers() {
        assertEquals(0, guildTest.getMembers().size());
    }

    @Test
    void setId() {
        Long idValue = 4L;
        guildTest.setId(idValue);
        assertEquals(idValue, guildTest.getId());
    }

    @Test
    void setName() {
        guildTest.setName("Test2");
        assertEquals("Test2", guildTest.getName());
    }

    @Test
    void setDescription() {
        guildTest.setDescription("Test2");
        assertEquals("Test2", guildTest.getDescription());
    }

    @Test
    void setTag() {
        guildTest.setTag("Test2");
        assertEquals("Test2", guildTest.getTag());
    }

    @Test
    void setLeader() {
        guildTest.setLeader("Test2");
        assertEquals("Test2", guildTest.getLeader());
    }

    @Test
    void setSubLeader() {
        guildTest.setSubLeader("Test2");
        assertEquals("Test2", guildTest.getSubLeader());
    }

    @Test
    void setLevel() {
        guildTest.setLevel((short) 2);
        assertEquals(2, guildTest.getLevel());
    }

    @Test
    void setPoints() {
        guildTest.setDiamonds(2);
        assertEquals(2, guildTest.getDiamonds());
    }

    @Test
    void setMembers() {
        guildTest.setMembers(null);
        assertNull(guildTest.getMembers());
    }
}