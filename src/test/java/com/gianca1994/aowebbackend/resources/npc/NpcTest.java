package com.gianca1994.aowebbackend.resources.npc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NpcTest {

    private Npc npcTest;

    @BeforeEach
    void setUp() {
        npcTest = new Npc();
        npcTest.setId(1L);
        npcTest.setName("Test");
        npcTest.setLevel((short) 1);
        npcTest.setGiveMinExp(1L);
        npcTest.setGiveMaxExp(1L);
        npcTest.setGiveMinGold(1L);
        npcTest.setGiveMaxGold(1L);
        npcTest.setHp(1);
        npcTest.setMaxHp(1);
        npcTest.setMinDmg(1);
        npcTest.setMaxDmg(1);
        npcTest.setDefense(1);
        npcTest.setZone("Test");
    }

    @Test
    public void constructorNotArgsTest() {
        Npc npc = new Npc();
        assertThat(npc).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Npc npc = new Npc(1L, "Test", (short) 1, 1L, 1L, 1L, 1L, 1, 1, 1, 1, 1, "Test");
        assertThat(npc).isNotNull();
    }

    @Test
    public void constructorAllArgsWithoutIdTest() {
        Npc npc = new Npc("Test", (short) 1, 1L, 1L, 1L, 1L, 1, 1, 1, 1, 1, "Test");
        assertThat(npc).isNotNull();
    }

    @Test
    void givenNpc_whenGetId_thenReturnId() {
        assertThat(npcTest.getId()).isEqualTo(1L);
    }

    @Test
    void givenNpc_whenGetName_thenReturnName() {
        assertThat(npcTest.getName()).isEqualTo("Test");
    }

    @Test
    void givenNpc_whenGetLevel_thenReturnLevel() {
        assertThat(npcTest.getLevel()).isEqualTo((short) 1);
    }

    @Test
    void givenNpc_whenGetGiveMinExp_thenReturnGiveMinExp() {
        assertThat(npcTest.getGiveMinExp()).isEqualTo(1L);
    }

    @Test
    void givenNpc_whenGetGiveMaxExp_thenReturnGiveMaxExp() {
        assertThat(npcTest.getGiveMaxExp()).isEqualTo(1L);
    }

    @Test
    void givenNpc_whenGetGiveMinGold_thenReturnGiveMinGold() {
        assertThat(npcTest.getGiveMinGold()).isEqualTo(1L);
    }

    @Test
    void givenNpc_whenGetGiveMaxGold_thenReturnGiveMaxGold() {
        assertThat(npcTest.getGiveMaxGold()).isEqualTo(1L);
    }

    @Test
    void givenNpc_whenGetHp_thenReturnHp() {
        assertThat(npcTest.getHp()).isEqualTo(1);
    }

    @Test
    void givenNpc_whenGetMaxHp_thenReturnMaxHp() {
        assertThat(npcTest.getMaxHp()).isEqualTo(1);
    }

    @Test
    void givenNpc_whenGetMinDmg_thenReturnMinDmg() {
        assertThat(npcTest.getMinDmg()).isEqualTo(1);
    }

    @Test
    void givenNpc_whenGetMaxDmg_thenReturnMaxDmg() {
        assertThat(npcTest.getMaxDmg()).isEqualTo(1);
    }

    @Test
    void givenNpc_whenGetDefense_thenReturnDefense() {
        assertThat(npcTest.getDefense()).isEqualTo(1);
    }

    @Test
    void givenNpc_whenGetZone_thenReturnZone() {
        assertThat(npcTest.getZone()).isEqualTo("Test");
    }

    @Test
    void givenNpc_whenEquals_thenFalse() {
        Npc npc = new Npc(1L, "Test", (short) 1, 1L, 1L, 1L, 1L, 1, 1, 1, 1, 1, "Test");
        assertThat(npcTest.equals(npc)).isFalse();
    }
}