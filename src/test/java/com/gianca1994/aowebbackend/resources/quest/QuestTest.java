package com.gianca1994.aowebbackend.resources.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QuestTest {

    private Quest quest;

    @BeforeEach
    void setUp() {
        quest = new Quest();
        quest.setId(1L);
        quest.setName("name");
        quest.setDescription("description");
        quest.setNameNpcKill("nameNpcKill");
        quest.setNpcKillAmount(1);
        quest.setNpcKillAmountNeeded(1);
        quest.setUserKillAmount(1);
        quest.setUserKillAmountNeeded(1);
        quest.setGiveExp(1L);
        quest.setGiveGold(1L);
        quest.setGiveDiamonds((short) 1);
    }

    @Test
    public void constructorNotArgsTest() {
        Quest quest = new Quest();
        assertThat(quest).isNotNull();
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
    public void constructorAllArgsWithoutIdTest() {
        Quest quest = new Quest(
                "name", "description",
                "nameNpcKill", 1,
                1,
                1,
                1,
                (short) 1
        );
        assertThat(quest).isNotNull();
    }

    @Test
    void givenQuest_whenGetId_thenReturnId() {
        assertThat(quest.getId()).isEqualTo(1L);
    }

    @Test
    void givenQuest_whenGetName_thenReturnName() {
        assertThat(quest.getName()).isEqualTo("name");
    }

    @Test
    void givenQuest_whenGetDescription_thenReturnDescription() {
        assertThat(quest.getDescription()).isEqualTo("description");
    }

    @Test
    void givenQuest_whenGetNameNpcKill_thenReturnNameNpcKill() {
        assertThat(quest.getNameNpcKill()).isEqualTo("nameNpcKill");
    }

    @Test
    void givenQuest_whenGetNpcKillAmount_thenReturnNpcKillAmount() {
        assertThat(quest.getNpcKillAmount()).isEqualTo(1);
    }

    @Test
    void givenQuest_whenGetNpcKillAmountNeeded_thenReturnNpcKillAmountNeeded() {
        assertThat(quest.getNpcKillAmountNeeded()).isEqualTo(1);
    }

    @Test
    void givenQuest_whenGetUserKillAmount_thenReturnUserKillAmount() {
        assertThat(quest.getUserKillAmount()).isEqualTo(1);
    }

    @Test
    void givenQuest_whenGetUserKillAmountNeeded_thenReturnUserKillAmountNeeded() {
        assertThat(quest.getUserKillAmountNeeded()).isEqualTo(1);
    }

    @Test
    void givenQuest_whenGetGiveExp_thenReturnGiveExp() {
        assertThat(quest.getGiveExp()).isEqualTo(1L);
    }

    @Test
    void givenQuest_whenGetGiveGold_thenReturnGiveGold() {
        assertThat(quest.getGiveGold()).isEqualTo(1L);
    }

    @Test
    void givenQuest_whenGetGiveDiamonds_thenReturnGiveDiamonds() {
        assertThat(quest.getGiveDiamonds()).isEqualTo((short) 1);
    }

    @Test
    void givenQuest_whenNotEquals_thenNotEquals() {
        Quest quest2 = new Quest(
                2L,
                "name2", "description2",
                "nameNpcKill2", 2,
                2,
                2,
                2,
                2L,
                2L,
                (short) 2
        );
        assertThat(quest).isNotEqualTo(quest2);
    }
}