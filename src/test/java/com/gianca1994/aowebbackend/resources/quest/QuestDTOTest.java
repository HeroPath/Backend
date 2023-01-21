package com.gianca1994.aowebbackend.resources.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QuestDTOTest {

    private QuestDTO questDTO;

    @BeforeEach
    void setUp() {
        questDTO = new QuestDTO(
                "name", "description",
                "nameNpcKill", 1,
                1,
                1L,
                1L,
                (short) 1
        );
    }

    @Test
    public void constructorNotArgsTest() {
        QuestDTO questDTO = new QuestDTO();
        assertThat(questDTO).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Quest quest = new Quest(
                1L, "name", "description", "nameNpcKill",
                1, 1,
                1, 1,
                1L, 1L, (short) 1);

        assertThat(quest).isNotNull();
    }

    @Test
    void givenQuestDTO_whenGetName_thenReturnName() {
        assertThat(questDTO.getName()).isEqualTo("name");
    }

    @Test
    void givenQuestDTO_whenGetDescription_thenReturnDescription() {
        assertThat(questDTO.getDescription()).isEqualTo("description");
    }

    @Test
    void givenQuestDTO_whenGetNameNpcKill_thenReturnNameNpcKill() {
        assertThat(questDTO.getNameNpcKill()).isEqualTo("nameNpcKill");
    }

    @Test
    void givenQuestDTO_whenGetNpcKillAmountNeeded_thenReturnNpcKillAmountNeeded() {
        assertThat(questDTO.getNpcKillAmountNeeded()).isEqualTo(1);
    }

    @Test
    void givenQuestDTO_whenGetUserKillAmountNeeded_thenReturnUserKillAmountNeeded() {
        assertThat(questDTO.getUserKillAmountNeeded()).isEqualTo(1);
    }

    @Test
    void givenQuestDTO_whenGetGiveExp_thenReturnGiveExp() {
        assertThat(questDTO.getGiveExp()).isEqualTo(1L);
    }

    @Test
    void givenQuestDTO_whenGetGiveGold_thenReturnGiveGold() {
        assertThat(questDTO.getGiveGold()).isEqualTo(1L);
    }

    @Test
    void givenQuestDTO_whenGetGiveDiamonds_thenReturnGiveDiamonds() {
        assertThat(questDTO.getGiveDiamonds()).isEqualTo((short) 1);
    }

    @Test
    void givenQuestDTO_whenSetName_thenReturnName() {
        questDTO.setName("name2");
        assertThat(questDTO.getName()).isEqualTo("name2");
    }

    @Test
    void givenQuestDTO_whenSetDescription_thenReturnDescription() {
        questDTO.setDescription("description2");
        assertThat(questDTO.getDescription()).isEqualTo("description2");
    }

    @Test
    void givenQuestDTO_whenSetNameNpcKill_thenReturnNameNpcKill() {
        questDTO.setNameNpcKill("nameNpcKill2");
        assertThat(questDTO.getNameNpcKill()).isEqualTo("nameNpcKill2");
    }

    @Test
    void givenQuestDTO_whenSetNpcKillAmountNeeded_thenReturnNpcKillAmountNeeded() {
        questDTO.setNpcKillAmountNeeded(2);
        assertThat(questDTO.getNpcKillAmountNeeded()).isEqualTo(2);
    }

    @Test
    void givenQuestDTO_whenSetUserKillAmountNeeded_thenReturnUserKillAmountNeeded() {
        questDTO.setUserKillAmountNeeded(2);
        assertThat(questDTO.getUserKillAmountNeeded()).isEqualTo(2);
    }

    @Test
    void givenQuestDTO_whenSetGiveExp_thenReturnGiveExp() {
        questDTO.setGiveExp(2L);
        assertThat(questDTO.getGiveExp()).isEqualTo(2L);
    }

    @Test
    void givenQuestDTO_whenSetGiveGold_thenReturnGiveGold() {
        questDTO.setGiveGold(2L);
        assertThat(questDTO.getGiveGold()).isEqualTo(2L);
    }

    @Test
    void givenQuestDTO_whenSetGiveDiamonds_thenReturnGiveDiamonds() {
        questDTO.setGiveDiamonds((short) 2);
        assertThat(questDTO.getGiveDiamonds()).isEqualTo((short) 2);
    }

    @Test
    void givenQuestDTO_whenNotEquals_thenReturnFalse() {
        QuestDTO questDTO2 = new QuestDTO(
                "name", "description",
                "nameNpcKill", 1,
                1,
                1L,
                1L,
                (short) 1
        );
        assertThat(questDTO.equals(questDTO2)).isFalse();
    }
}