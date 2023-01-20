package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        Item item = new Item(1L,"Test", "Test", 1, "Test", 1, 1, 1, 1, 1, 1);
        assertThat(item).isNotNull();
    }

    @Test
    public void constructorAllArgsWithoutIdTest() {
        Item item = new Item("Test", "Test", 1, "Test", 1, 1, 1, 1, 1, 1);
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
    public void givenItem_whenSetId_thenReturnId() {
        itemTest.setId(2L);
        assertThat(itemTest.getId()).isEqualTo(2L);
    }

    @Test
    public void givenItem_whenSetName_thenReturnName() {
        itemTest.setName("Test2");
        assertThat(itemTest.getName()).isEqualTo("Test2");
    }

    @Test
    public void givenItem_whenSetType_thenReturnType() {
        itemTest.setType("Test2");
        assertThat(itemTest.getType()).isEqualTo("Test2");
    }

    @Test
    public void givenItem_whenSetLvlMin_thenReturnLvlMin() {
        itemTest.setLvlMin(2);
        assertThat(itemTest.getLvlMin()).isEqualTo(2);
    }

    @Test
    public void givenItem_whenSetClassRequired_thenReturnClassRequired() {
        itemTest.setClassRequired("Test2");
        assertThat(itemTest.getClassRequired()).isEqualTo("Test2");
    }

    @Test
    public void givenItem_whenSetPrice_thenReturnPrice() {
        itemTest.setPrice(2);
        assertThat(itemTest.getPrice()).isEqualTo(2);
    }

    @Test
    public void givenItem_whenSetStrength_thenReturnStrength() {
        itemTest.setStrength(2);
        assertThat(itemTest.getStrength()).isEqualTo(2);
    }

    @Test
    public void givenItem_whenSetDexterity_thenReturnDexterity() {
        itemTest.setDexterity(2);
        assertThat(itemTest.getDexterity()).isEqualTo(2);
    }

    @Test
    public void givenItem_whenSetIntelligence_thenReturnIntelligence() {
        itemTest.setIntelligence(2);
        assertThat(itemTest.getIntelligence()).isEqualTo(2);
    }

    @Test
    public void givenItem_whenSetVitality_thenReturnVitality() {
        itemTest.setVitality(2);
        assertThat(itemTest.getVitality()).isEqualTo(2);
    }

    @Test
    public void givenItem_whenSetLuck_thenReturnLuck() {
        itemTest.setLuck(2);
        assertThat(itemTest.getLuck()).isEqualTo(2);
    }

    @Test
    public void givenItem_whenEquals_thenReturnFalse() {
        Item item = new Item(1L,"Test", "Test", 1, "Test", 1, 1, 1, 1, 1, 1);
        assertThat(itemTest.equals(item)).isFalse();
    }
}