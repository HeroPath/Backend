package com.gianca1994.aowebbackend.resources.quest;

import com.gianca1994.aowebbackend.resources.quest.dto.request.QuestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QuestDTOTest {

    private QuestDTO questDTO;

    @BeforeEach
    void setUp() {
        questDTO = new QuestDTO();
        questDTO.setName("name");
        questDTO.setNameNpcKill("nameNpcKill");
        questDTO.setNpcAmountNeed(1);
        questDTO.setUserAmountNeed(1);
        questDTO.setGiveExp(1);
        questDTO.setGiveGold(1L);
        questDTO.setGiveDiamonds((short) 1);
    }

    @Test
    public void constructorNotArgsTest() {
        QuestDTO questDTO = new QuestDTO();
        assertThat(questDTO).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
       QuestDTO questDTO = new QuestDTO(
                "name",
                "nameNpcKill", 1,
                1,
                1,
                1L,
                (short) 1
        );
        assertThat(questDTO).isNotNull();
    }

    @Test
    void givenQuestDTO_whenGetName_thenReturnName() {
        assertThat(questDTO.getName()).isEqualTo("name");
    }


    @Test
    void givenQuestDTO_whenGetNameNpcKill_thenReturnNameNpcKill() {
        assertThat(questDTO.getNameNpcKill()).isEqualTo("nameNpcKill");
    }

    @Test
    void givenQuestDTO_whenGetNpcKillAmountNeeded_thenReturnNpcKillAmountNeeded() {
        assertThat(questDTO.getNpcAmountNeed()).isEqualTo(1);
    }

    @Test
    void givenQuestDTO_whenGetUserKillAmountNeeded_thenReturnUserKillAmountNeeded() {
        assertThat(questDTO.getUserAmountNeed()).isEqualTo(1);
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
    void givenQuestDTO_whenNotEquals_thenReturnFalse() {
        QuestDTO questDTO2 = new QuestDTO(
                "name",
                "nameNpcKill", 1,
                1,
                1,
                1L,
                (short) 1
        );
        assertThat(questDTO.equals(questDTO2)).isFalse();
    }
}