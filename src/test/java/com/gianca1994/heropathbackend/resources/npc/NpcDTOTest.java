package com.gianca1994.heropathbackend.resources.npc;

import com.gianca1994.heropathbackend.resources.npc.dto.request.NpcDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NpcDTOTest {

    private NpcDTO npcDTO;

    @BeforeEach
    void setUp() {
        npcDTO = new NpcDTO();
        npcDTO.setName("Test");
        npcDTO.setLevel((short) 1);
        npcDTO.setGiveMinExp(1);
        npcDTO.setGiveMaxExp(1);
        npcDTO.setGiveMinGold(1L);
        npcDTO.setGiveMaxGold(1L);
        npcDTO.setHp(1);
        npcDTO.setMaxHp(1);
        npcDTO.setMinDmg(1);
        npcDTO.setMaxDmg(1);
        npcDTO.setDefense(1);
        npcDTO.setZone("Test");
    }

    @Test
    public void constructorNotArgsTest() {
        NpcDTO npcDTO = new NpcDTO();
        assertThat(npcDTO).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        NpcDTO npcDTO = new NpcDTO("Test", (short) 1, 1, 1, 1L, 1L, 1, 1, 1, 1, 1, "Test");
        assertThat(npcDTO).isNotNull();
    }


    @Test
    void givenNpcDTO_whenGetName_thenReturnName() {
        assertThat(npcDTO.getName()).isEqualTo("Test");
    }

    @Test
    void givenNpcDTO_whenGetLevel_thenReturnLevel() {
        assertThat(npcDTO.getLevel()).isEqualTo((short) 1);
    }

    @Test
    void givenNpcDTO_whenGetGiveMinExp_thenReturnGiveMinExp() {
        assertThat(npcDTO.getGiveMinExp()).isEqualTo(1L);
    }

    @Test
    void givenNpcDTO_whenGetGiveMaxExp_thenReturnGiveMaxExp() {
        assertThat(npcDTO.getGiveMaxExp()).isEqualTo(1L);
    }

    @Test
    void givenNpcDTO_whenGetGiveMinGold_thenReturnGiveMinGold() {
        assertThat(npcDTO.getGiveMinGold()).isEqualTo(1L);
    }

    @Test
    void givenNpcDTO_whenGetGiveMaxGold_thenReturnGiveMaxGold() {
        assertThat(npcDTO.getGiveMaxGold()).isEqualTo(1L);
    }

    @Test
    void givenNpcDTO_whenGetHp_thenReturnHp() {
        assertThat(npcDTO.getHp()).isEqualTo(1);
    }

    @Test
    void givenNpcDTO_whenGetMaxHp_thenReturnMaxHp() {
        assertThat(npcDTO.getMaxHp()).isEqualTo(1);
    }

    @Test
    void givenNpcDTO_whenGetMinDmg_thenReturnMinDmg() {
        assertThat(npcDTO.getMinDmg()).isEqualTo(1);
    }

    @Test
    void givenNpcDTO_whenGetMaxDmg_thenReturnMaxDmg() {
        assertThat(npcDTO.getMaxDmg()).isEqualTo(1);
    }

    @Test
    void givenNpcDTO_whenGetDefense_thenReturnDefense() {
        assertThat(npcDTO.getDefense()).isEqualTo(1);
    }

    @Test
    void givenNpcDTO_whenGetZone_thenReturnZone() {
        assertThat(npcDTO.getZone()).isEqualTo("Test");
    }

    @Test
    void givenNpcDTO_whenEquals_thenReturnFalse() {
        NpcDTO npcDTO2 = new NpcDTO("Test", (short) 1, 1, 1, 1L, 1L, 1, 1, 1, 1, 1, "Test");
        assertThat(npcDTO.equals(npcDTO2)).isFalse();
    }
}