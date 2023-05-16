package com.gianca1994.heropathbackend.resources.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ItemTest {

    private Item itemTest;

    @BeforeEach
    void setUp() {
        itemTest = new Item();
        itemTest.setId(1L);
        itemTest.setName("Test");
        itemTest.setType("Test");
        itemTest.setLvlMin(1);
        itemTest.setClassRequired("Test");
        itemTest.setPrice(1);
        itemTest.setStrength(1);
        itemTest.setDexterity(1);
        itemTest.setIntelligence(1);
        itemTest.setVitality(1);
        itemTest.setLuck(1);
    }

    @Test
    public void constructorNotArgsTest() {
        Item item = new Item();
        assertThat(item).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Item item = new Item(1L, "Test", "Test", 1, 1, "Test", "test", 1, 1, 1, 1, 1, 1, null, false, false);
        assertThat(item).isNotNull();
    }

    @Test
    public void constructorAllArgsWithoutIdTest() {
        Item item = new Item("Test", "Test", 1, 1, "Test", 1, 1, 1, 1, 1, true);
        assertThat(item).isNotNull();
    }

    @Test
    public void givenItem_whenGetId_thenReturnId() {
        assertThat(itemTest.getId()).isEqualTo(1L);
    }

    @Test
    public void givenItem_whenGetName_thenReturnName() {
        assertThat(itemTest.getName()).isEqualTo("Test");
    }

    @Test
    public void givenItem_whenGetType_thenReturnType() {
        assertThat(itemTest.getType()).isEqualTo("Test");
    }

    @Test
    public void givenItem_whenGetLvlMin_thenReturnLvlMin() {
        assertThat(itemTest.getLvlMin()).isEqualTo(1);
    }

    @Test
    public void givenItem_whenGetClassRequired_thenReturnClassRequired() {
        assertThat(itemTest.getClassRequired()).isEqualTo("Test");
    }

    @Test
    public void givenItem_whenGetPrice_thenReturnPrice() {
        assertThat(itemTest.getPrice()).isEqualTo(1);
    }

    @Test
    public void givenItem_whenGetStrength_thenReturnStrength() {
        assertThat(itemTest.getStrength()).isEqualTo(1);
    }

    @Test
    public void givenItem_whenGetDexterity_thenReturnDexterity() {
        assertThat(itemTest.getDexterity()).isEqualTo(1);
    }

    @Test
    public void givenItem_whenGetIntelligence_thenReturnIntelligence() {
        assertThat(itemTest.getIntelligence()).isEqualTo(1);
    }

    @Test
    public void givenItem_whenGetVitality_thenReturnVitality() {
        assertThat(itemTest.getVitality()).isEqualTo(1);
    }

    @Test
    public void givenItem_whenGetLuck_thenReturnLuck() {
        assertThat(itemTest.getLuck()).isEqualTo(1);
    }

    @Test
    public void givenItem_whenEquals_thenReturnFalse() {
        Item item = new Item(1L, "Test", "Test", 1, 1, "Test", "test", 1, 1, 1, 1, 1, 1, null, true, false);
        assertThat(itemTest.equals(item)).isFalse();
    }
}