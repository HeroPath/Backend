package com.gianca1994.aowebbackend.resources.title;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class TitleTest {

    private Title titleTest;

    @BeforeEach
    void setUp() {
        titleTest = new Title();
        titleTest.setId(1L);
        titleTest.setName("Test");
        titleTest.setMinLvl(1);
        titleTest.setMinPts(1);
        titleTest.setStrength(1);
        titleTest.setDexterity(1);
        titleTest.setIntelligence(1);
        titleTest.setVitality(1);
        titleTest.setLuck(1);
    }

    @Test
    public void constructorNotArgsTest() {
        Title titleTest2 = new Title();
        assertThat(titleTest2).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Title titleTest2 = new Title(1L, "Test", 1, 1, 1, 1, 1, 1, 1);
        assertThat(titleTest2).isNotNull();
    }

    @Test
    public void constructorNotAllArgsTest(){
        Title titleTest2 = new Title("Test", 1, 1, 1, 1, 1, 1, 1);
        assertThat(titleTest2).isNotNull();
    }

    @Test
    void givenTitle_whenGetId_thenReturnId() {
        assertThat(titleTest.getId()).isEqualTo(1L);
    }

    @Test
    void givenTitle_whenGetName_thenReturnName() {
        assertThat(titleTest.getName()).isEqualTo("Test");
    }

    @Test
    void givenTitle_whenGetMinLvl_thenReturnMinLvl() {
        assertThat(titleTest.getMinLvl()).isEqualTo(1);
    }

    @Test
    void givenTitle_whenGetMinPts_thenReturnMinPts() {
        assertThat(titleTest.getMinPts()).isEqualTo(1);
    }

    @Test
    void givenTitle_whenGetStrength_thenReturnStrength() {
        assertThat(titleTest.getStrength()).isEqualTo(1);
    }

    @Test
    void givenTitle_whenGetDexterity_thenReturnDexterity() {
        assertThat(titleTest.getDexterity()).isEqualTo(1);
    }

    @Test
    void givenTitle_whenGetIntelligence_thenReturnIntelligence() {
        assertThat(titleTest.getIntelligence()).isEqualTo(1);
    }

    @Test
    void givenTitle_whenGetVitality_thenReturnVitality() {
        assertThat(titleTest.getVitality()).isEqualTo(1);
    }

    @Test
    void givenTitle_whenGetLuck_thenReturnLuck() {
        assertThat(titleTest.getLuck()).isEqualTo(1);
    }

    @Test
    void givenTitle_whenSetId_thenReturnId() {
        titleTest.setId(2L);
        assertThat(titleTest.getId()).isEqualTo(2L);
    }

    @Test
    void givenTitle_whenSetName_thenReturnName() {
        titleTest.setName("Test2");
        assertThat(titleTest.getName()).isEqualTo("Test2");
    }

    @Test
    void givenTitle_whenSetMinLvl_thenReturnMinLvl() {
        titleTest.setMinLvl(2);
        assertThat(titleTest.getMinLvl()).isEqualTo(2);
    }

    @Test
    void givenTitle_whenSetMinPts_thenReturnMinPts() {
        titleTest.setMinPts(2);
        assertThat(titleTest.getMinPts()).isEqualTo(2);
    }

    @Test
    void givenTitle_whenSetStrength_thenReturnStrength() {
        titleTest.setStrength(2);
        assertThat(titleTest.getStrength()).isEqualTo(2);
    }

    @Test
    void givenTitle_whenSetDexterity_thenReturnDexterity() {
        titleTest.setDexterity(2);
        assertThat(titleTest.getDexterity()).isEqualTo(2);
    }

    @Test
    void givenTitle_whenSetIntelligence_thenReturnIntelligence() {
        titleTest.setIntelligence(2);
        assertThat(titleTest.getIntelligence()).isEqualTo(2);
    }

    @Test
    void givenTitle_whenSetVitality_thenReturnVitality() {
        titleTest.setVitality(2);
        assertThat(titleTest.getVitality()).isEqualTo(2);
    }

    @Test
    void givenTitle_whenSetLuck_thenReturnLuck() {
        titleTest.setLuck(2);
        assertThat(titleTest.getLuck()).isEqualTo(2);
    }
}