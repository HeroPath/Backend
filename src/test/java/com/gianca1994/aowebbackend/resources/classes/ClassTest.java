package com.gianca1994.aowebbackend.resources.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ClassTest {

    private Class classTest;

    @BeforeEach
    void setUp() {
        classTest = new Class();
        classTest.setId(1L);
        classTest.setName("Test");
        classTest.setStrength(1);
        classTest.setDexterity(1);
        classTest.setIntelligence(1);
        classTest.setVitality(1);
        classTest.setLuck(1);
    }

    @Test
    public void constructorNotArgsTest() {
        Class classTest = new Class();
        assertThat(classTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Class classTest2 = new Class(2L, "Test2", 2, 2, 2, 2, 2);
        assertThat(classTest2).isNotNull();
    }

    @Test
    public void givenClass_whenGetId_thenReturnId() {
        assertThat(classTest.getId()).isEqualTo(1L);
    }

    @Test
    public void givenClass_whenGetName_thenReturnName() {
        assertThat(classTest.getName()).isEqualTo("Test");
    }

    @Test
    public void givenClass_whenGetStrength_thenReturnStrength() {
        assertThat(classTest.getStrength()).isEqualTo(1);
    }

    @Test
    public void givenClass_whenGetDexterity_thenReturnDexterity() {
        assertThat(classTest.getDexterity()).isEqualTo(1);
    }

    @Test
    public void givenClass_whenGetIntelligence_thenReturnIntelligence() {
        assertThat(classTest.getIntelligence()).isEqualTo(1);
    }

    @Test
    public void givenClass_whenGetVitality_thenReturnVitality() {
        assertThat(classTest.getVitality()).isEqualTo(1);
    }

    @Test
    public void givenClass_whenGetLuck_thenReturnLuck() {
        assertThat(classTest.getLuck()).isEqualTo(1);
    }

    @Test
    public void givenClass_whenSetId_thenSetId() {
        classTest.setId(2L);
        assertThat(classTest.getId()).isEqualTo(2L);
    }

    @Test
    public void givenClass_whenSetName_thenSetName() {
        classTest.setName("Test2");
        assertThat(classTest.getName()).isEqualTo("Test2");
    }

    @Test
    public void givenClass_whenSetStrength_thenSetStrength() {
        classTest.setStrength(2);
        assertThat(classTest.getStrength()).isEqualTo(2);
    }

    @Test
    public void givenClass_whenSetDexterity_thenSetDexterity() {
        classTest.setDexterity(2);
        assertThat(classTest.getDexterity()).isEqualTo(2);
    }

    @Test
    public void givenClass_whenSetIntelligence_thenSetIntelligence() {
        classTest.setIntelligence(2);
        assertThat(classTest.getIntelligence()).isEqualTo(2);
    }

    @Test
    public void givenClass_whenSetVitality_thenSetVitality() {
        classTest.setVitality(2);
        assertThat(classTest.getVitality()).isEqualTo(2);
    }

    @Test
    public void givenClass_whenSetLuck_thenSetLuck() {
        classTest.setLuck(2);
        assertThat(classTest.getLuck()).isEqualTo(2);
    }

    @Test
    public void givenClass_whenEquals_thenFalse() {
        Class classTest2 = new Class(2L, "Test2", 2, 2, 2, 2, 2);
        assertThat(classTest.equals(classTest2)).isFalse();
    }
}
