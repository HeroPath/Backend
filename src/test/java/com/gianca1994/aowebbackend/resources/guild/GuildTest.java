package com.gianca1994.aowebbackend.resources.guild;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GuildTest {

    private Guild guildTest;

    @BeforeEach
    void setUp() {
        guildTest = new Guild();
        guildTest.setId(1L);
        guildTest.setName("Test");
        guildTest.setDescription("Test");
        guildTest.setTag("Test");
        guildTest.setLeader("Test");
        guildTest.setSubLeader("Test");
        guildTest.setLevel((short) 1);
        guildTest.setDiamonds(1);
        guildTest.setTitlePoints(1);
        guildTest.setMembers(new ArrayList<>());
        guildTest.setRequests(new HashSet<>());
    }

    @Test
    void constructorNotArgsTest() {
        Guild guild = new Guild();
        assertNotNull(guild);
    }

    @Test
    void constructorAllArgsTest() {
        Guild guild = new Guild(
                1L,
                "Test",
                "Test",
                "Test",
                "Test",
                "Test",
                (short) 1,
                1,
                1,
                new ArrayList<>(),
                new HashSet<>()
        );
        assertNotNull(guild);
    }

    @Test
    void constructorArgsTest() {
        Guild guild = new Guild(
                "Test",
                "Test",
                "Test",
                "Test",
                (short) 1,
                1
        );
        assertNotNull(guild);
    }

    @Test
    void givenGuild_whenGetId_thenReturnId() {
        assertEquals(1L, guildTest.getId());
    }

    @Test
    void givenGuild_whenGetName_thenReturnName() {
        assertEquals("Test", guildTest.getName());
    }

    @Test
    void givenGuild_whenGetDescription_thenReturnDescription() {
        assertEquals("Test", guildTest.getDescription());
    }

    @Test
    void givenGuild_whenGetTag_thenReturnTag() {
        assertEquals("Test", guildTest.getTag());
    }

    @Test
    void givenGuild_whenGetLeader_thenReturnLeader() {
        assertEquals("Test", guildTest.getLeader());
    }

    @Test
    void givenGuild_whenGetSubLeader_thenReturnSubLeader() {
        assertEquals("Test", guildTest.getSubLeader());
    }

    @Test
    void givenGuild_whenGetLevel_thenReturnLevel() {
        assertEquals(1, guildTest.getLevel());
    }

    @Test
    void givenGuild_whenGetDiamonds_thenReturnDiamonds() {
        assertEquals(1, guildTest.getDiamonds());
    }

    @Test
    void givenGuild_whenGetTitlePoints_thenReturnTitlePoints() {
        assertEquals(1, guildTest.getTitlePoints());
    }

    @Test
    void givenGuild_whenGetMembers_thenReturnMembers() {
        assertEquals(0, guildTest.getMembers().size());
    }

    @Test
    void givenGuild_whenGetRequests_thenReturnRequests() {
        assertEquals(0, guildTest.getRequests().size());
    }

    @Test
    void givenGuild_whenSetId_thenReturnId() {
        guildTest.setId(10L);
        assertEquals(10L, guildTest.getId());
    }

    @Test
    void givenGuild_whenSetName_thenReturnName() {
        guildTest.setName("Test2");
        assertEquals("Test2", guildTest.getName());
    }

    @Test
    void givenGuild_whenSetDescription_thenReturnDescription() {
        guildTest.setDescription("Test2");
        assertEquals("Test2", guildTest.getDescription());
    }

    @Test
    void givenGuild_whenSetTag_thenReturnTag() {
        guildTest.setTag("Test2");
        assertEquals("Test2", guildTest.getTag());
    }

    @Test
    void givenGuild_whenSetLeader_thenReturnLeader() {
        guildTest.setLeader("Test2");
        assertEquals("Test2", guildTest.getLeader());
    }

    @Test
    void givenGuild_whenSetSubLeader_thenReturnSubLeader() {
        guildTest.setSubLeader("Test2");
        assertEquals("Test2", guildTest.getSubLeader());
    }

    @Test
    void givenGuild_whenSetLevel_thenReturnLevel() {
        guildTest.setLevel((short) 2);
        assertEquals(2, guildTest.getLevel());
    }

    @Test
    void givenGuild_whenSetDiamonds_thenReturnDiamonds() {
        guildTest.setDiamonds(2);
        assertEquals(2, guildTest.getDiamonds());
    }

    @Test
    void givenGuild_whenSetTitlePoints_thenReturnTitlePoints() {
        guildTest.setTitlePoints(2);
        assertEquals(2, guildTest.getTitlePoints());
    }

    @Test
    void givenGuild_whenSetMembers_thenReturnMembers() {
        guildTest.setMembers(new ArrayList<>());
        assertEquals(0, guildTest.getMembers().size());
    }

    @Test
    void givenGuild_whenSetRequests_thenReturnRequests() {
        guildTest.setRequests(new HashSet<>());
        assertEquals(0, guildTest.getRequests().size());
    }
}